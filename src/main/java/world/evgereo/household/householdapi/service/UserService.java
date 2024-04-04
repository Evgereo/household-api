package world.evgereo.household.householdapi.service;

import world.evgereo.household.householdapi.entity.User;
import world.evgereo.household.householdapi.entity.dto.UserDto;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long userId);

    User getUserByName(String name);

    User createUser(UserDto userDto);

    User updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);
}
