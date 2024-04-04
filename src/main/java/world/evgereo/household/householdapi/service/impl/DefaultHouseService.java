package world.evgereo.household.householdapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import world.evgereo.household.householdapi.entity.House;
import world.evgereo.household.householdapi.entity.User;
import world.evgereo.household.householdapi.entity.dto.HouseDto;
import world.evgereo.household.householdapi.exception.BadRequestException;
import world.evgereo.household.householdapi.exception.NotFoundException;
import world.evgereo.household.householdapi.repository.HouseRepository;
import world.evgereo.household.householdapi.service.HouseService;
import world.evgereo.household.householdapi.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultHouseService implements HouseService {
    private final HouseRepository houseRepository;
    private final UserService userService;

    @Override
    public List<House> getHouses() {
        return houseRepository.findAll();
    }

    @Override
    public House getHouse(Long houseId) {
        Optional<House> optionalHouse = houseRepository.findById(houseId);
        if (optionalHouse.isEmpty())
            throw new NotFoundException("House not found");
        return optionalHouse.get();
    }

    @Override
    public House createHouse(HouseDto houseDto) {
        House house = new House();
        house.setAddress(houseDto.address());
        house.setOwner(userService.getUser((Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return houseRepository.save(house);
    }

    @Override
    @Transactional
    public House updateHouse(Long houseId, HouseDto houseDto) {
        House house;
        try {
            house = getHouse(houseId);
        } catch (NotFoundException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        house.setAddress(houseDto.address());
        return houseRepository.save(house);
    }

    @Override
    @Transactional
    public House addResident(Long houseId, Long userId) {
        House house;
        User user;
        try {
            house = getHouse(houseId);
            user = userService.getUser(userId);
        } catch (NotFoundException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        List<User> residents = house.getResidents();
        if (!residents.contains(user)) {
            residents.add(user);
            house.setResidents(residents);
        } else
            throw new BadRequestException("user already lives in the house");
        return houseRepository.save(house);
    }

    @Override
    public House removeResident(Long houseId, Long userId) {
        House house;
        try {
            house = getHouse(houseId);
        } catch (NotFoundException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        house.setResidents(house.getResidents().stream()
                    .filter(user -> !user.getId().equals(userId))
                    .collect(Collectors.toList()));
        return houseRepository.save(house);
    }

    @Override
    public House removeAllResidents(Long houseId) {
        House house;
        try {
            house = getHouse(houseId);
        } catch (NotFoundException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        house.setResidents(null);
        return houseRepository.save(house);
    }

    @Override
    @Transactional
    public void deleteHouse(Long houseId) {
        if (!houseRepository.existsById(houseId)) {
            throw new BadRequestException("House not found");
        }
        houseRepository.deleteById(houseId);
    }
}
