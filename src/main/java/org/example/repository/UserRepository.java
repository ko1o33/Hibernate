package org.example.repository;

import org.example.dto.UserDto;
import org.example.entity.User;

public interface UserRepository {

    User saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(int id);

    void deleteUser(User user);

    void updateUser(User user);

}
