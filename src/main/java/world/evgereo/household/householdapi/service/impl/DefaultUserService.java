package world.evgereo.household.householdapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import world.evgereo.household.householdapi.entity.User;
import world.evgereo.household.householdapi.entity.dto.UserDto;
import world.evgereo.household.householdapi.exception.BadRequestException;
import world.evgereo.household.householdapi.exception.NotFoundException;
import world.evgereo.household.householdapi.repository.UserRepository;
import world.evgereo.household.householdapi.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            throw new NotFoundException("User not found");
        return optionalUser.get();
    }

    @Override
    public User getUserByName(String name) {
        Optional<User> optionalUser = userRepository.findByName(name);
        if (optionalUser.isEmpty())
            throw new NotFoundException("User not found");
        return optionalUser.get();
    }

    @Override
    public User createUser(UserDto userDto) {
        checkPasswordsMatching(userDto.password(), userDto.passwordConfirm());
        if (userRepository.existsByName(userDto.name()))
            throw new BadRequestException("Name should be unique");
        User user = User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .password(passwordEncoder.encode(userDto.password()))
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserDto userDto) {
        checkPasswordsMatching(userDto.password(), userDto.passwordConfirm());
        User user;
        try {
            user = getUser(userId);
        } catch (NotFoundException ex) {
            throw new BadRequestException(ex.getMessage());
        }
        user.setName(userDto.name());
        user.setAge(userDto.age());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("User not found");
        }
        userRepository.deleteById(userId);
    }

    private void checkPasswordsMatching(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new BadRequestException("Passwords don't match");
        }
    }
}
