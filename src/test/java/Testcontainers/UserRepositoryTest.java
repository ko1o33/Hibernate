package Testcontainers;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.MyException;
import org.example.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends TestcontainersConfiguration {

    private UserRepositoryImpl userRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        userRepository = new UserRepositoryImpl(sessionFactory);
    }

    @Test
    void saveUserTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        User savedUser = userRepository.saveUser(userDto);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("test", savedUser.getName());
        assertEquals(30, savedUser.getAge());
    }

    @Test
    void findUserByEmailTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        User savedUser = userRepository.saveUser(userDto);
        User foundUser = userRepository.findUserByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(userDto.getEmail(), foundUser.getEmail());
        assertEquals(userDto.getName(), foundUser.getName());
    }

    @Test
    void findUserByIdTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        User savedUser = userRepository.saveUser(userDto);
        User foundUser = userRepository.findUserById(savedUser.getId());
        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(userDto.getEmail(), foundUser.getEmail());
        assertEquals(userDto.getName(), foundUser.getName());
    }

    @Test
    void deleteUserTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        User savedUser = userRepository.saveUser(userDto);
        userRepository.deleteUser(savedUser);
        assertThrows(MyException.class, () -> {
            userRepository.findUserByEmail("test@example.com");
        });
    }

    @Test
    void updateUserTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        User savedUser = userRepository.saveUser(userDto);
        savedUser.setName("New Name");
        savedUser.setAge(35);
        userRepository.updateUser(savedUser);
        User updatedUser = userRepository.findUserByEmail("test@example.com");
        assertEquals("New Name", updatedUser.getName());
        assertEquals(35, updatedUser.getAge());
        assertEquals("test@example.com", updatedUser.getEmail());
    }


}
