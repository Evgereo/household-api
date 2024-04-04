package world.evgereo.household.householdapi.service;

import world.evgereo.household.householdapi.entity.House;
import world.evgereo.household.householdapi.entity.dto.HouseDto;

import java.util.List;

public interface HouseService {
    List<House> getHouses();

    House getHouse(Long houseId);

    House createHouse(HouseDto houseDto);

    House updateHouse(Long houseId, HouseDto houseDto);

    House addResident(Long houseId, Long userId);

    House removeResident(Long houseId, Long userId);

    House removeAllResidents(Long houseId);

    void deleteHouse(Long houseId);
}
