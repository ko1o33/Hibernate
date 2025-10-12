package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.mistake.MyMistake;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;

@Slf4j
public class CRUD_OperationImpl implements CRUD_Operation {

    private static final CRUD_OperationImpl instance = new CRUD_OperationImpl();

    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Transaction transaction;

    private CRUD_OperationImpl() {
        configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
        transaction= null;
    }

    public static CRUD_OperationImpl getInstance() {
        return instance;
    }


    @Override
    public void saveUser(UserDto userDto) {
        try (var session = sessionFactory.openSession()) {
            log.info("Save UserDto : {}", userDto.toString());
            transaction = session.beginTransaction();
            var user = User.builder()
                    .age(userDto.getAge())
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .created_at(LocalDateTime.now())
                    .build();
            session.persist(user);
            transaction.commit();
            log.info("User saved successfully with ID: {}", user.toString());
        }catch (Exception e){
            log.error("Error occurred :"+e.getMessage());
            throw e;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "FROM User WHERE email = :email";
        try (var session = sessionFactory.openSession()) {
            log.info("FindByEmail email : {}", email);
            User user = session.createQuery(sql, User.class).setParameter("email", email).uniqueResult();
            if (user == null) {
                log.info("User not found with email : {}", email);
                throw new MyMistake("User not found");
            }
            log.info("User found with email : {}", email);
            return  user;
        }catch (Exception e){
            log.error("Error occurred :"+e.getMessage());
            throw e;
        }
    }

    @Override
    public User findUserById(int id) {
        try (var session = sessionFactory.openSession()) {
            log.info("FindById id : {}", id);
            User user = session.find(User.class, id);
            if (user == null) {
                log.info("User not found with id : {}", id);
                throw new MyMistake("User not found");
            }
            log.info("User found with id : {}", id);
            return  user;
        }catch (Exception e){
            log.error("Error occurred :"+e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteUser(User user) {
        try (var session = sessionFactory.openSession()) {
            log.info("Delete user : {}", user.toString());
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            log.info("User delete user : {}", user.toString());
        }catch (Exception e){
            log.error("Error occurred :"+e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateUser(User user) {
        try (var session = sessionFactory.openSession()) {
            log.info("Update user : {}", user.toString());
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.info("User update user : {}", user.toString());
        }catch (Exception e){
            log.error("Error occurred :"+e.getMessage());
            throw e;
        }
    }
}
