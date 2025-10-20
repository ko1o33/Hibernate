package TestMockito;

import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.MyException;
import org.example.repository.UserRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<User> query;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(sessionFactory);
    }

    @Test
    void saveUserTest() {
        UserDto userDto = new UserDto("test", "test@example.com", 30);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        User result = userRepository.saveUser(userDto);
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("test", result.getName());
        assertEquals(30, result.getAge());
        assertNotNull(result.getCreated_at());
    }

    @Test
    void findUserByEmailTest() {
        String email = "test@example.com";
        User expectedUser = User.builder()
                .id(1)
                .email(email)
                .name("test")
                .age(30)
                .created_at(LocalDateTime.now())
                .build();

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.createQuery("FROM User WHERE email = :email", User.class)).thenReturn(query);
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(expectedUser);
        User foundUser = userRepository.findUserByEmail(email);
        assertNotNull(foundUser);
        assertEquals(expectedUser.getId(), foundUser.getId());
        assertEquals(email, foundUser.getEmail());
        assertEquals("test", foundUser.getName());
    }

    @Test
    void findUserByIdTest() {
        String email = "test@example.com";
        User expectedUser = User.builder()
                .id(1)
                .email(email)
                .name("test")
                .age(30)
                .created_at(LocalDateTime.now())
                .build();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.find(User.class, 1)).thenReturn(expectedUser);
        User foundUser = userRepository.findUserById(1);
        assertNotNull(foundUser);
        assertEquals(expectedUser.getId(), foundUser.getId());
        assertEquals(email, foundUser.getEmail());
        assertEquals("test", foundUser.getName());
    }

    @Test
    void deleteUserTest() {
        User userToDelete = User.builder()
                .id(1)
                .email("test@example.com")
                .name("test")
                .age(30)
                .build();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        userRepository.deleteUser(userToDelete);
        verify(session).remove(userToDelete);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void updateUserTest() {
        User userToUpdate = User.builder()
                .id(1)
                .email("test@example.com")
                .name("New Name")
                .age(35)
                .build();
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.merge(userToUpdate)).thenReturn(userToUpdate);
        userRepository.updateUser(userToUpdate);
        verify(session).merge(userToUpdate);
        verify(transaction).commit();
        verify(session).close();
    }
}
