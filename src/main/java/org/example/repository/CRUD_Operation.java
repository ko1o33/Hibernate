package org.example.repository;

import org.example.dto.UserDto;
import org.example.entity.User;

import java.util.Optional;

public interface CRUD_Operation {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserById(int id);

    void deleteUser(User user);

    void updateUser(User user);

}
