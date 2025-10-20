package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.exception.MyException;
import org.example.repository.UserRepository;
import org.example.repository.UserRepositoryImpl;

import java.util.Scanner;

@Slf4j
public class Menu {

    private UserRepository crudOperation = UserRepositoryImpl.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int choice;
        while (true) {
            System.out.println("Выберите оперцию");
            System.out.println("1.Создать user");
            System.out.println("2.Найти user");
            System.out.println("3.Выход");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    findUser();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Такой операции нету");
                    break;
            }
        }
    }

    private void findUser() {
        int choice;
        User user = null;
        while (true) {
            try {
                System.out.println("Как найти user");
                System.out.println("1.По email");
                System.out.println("2.По id");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Ведите email");
                        String email = scanner.next();
                        user = crudOperation.findUserByEmail(email);
                        workUser(user);
                        return;
                    case 2:
                        System.out.println("Ведите id");
                        int id = scanner.nextInt();
                        user = crudOperation.findUserById(id);
                        workUser(user);
                        return;
                    default:
                        System.out.println("Такой операции нету");
                        break;
                }
            } catch (MyException e) {
                System.out.println("Произошла ошикба");
                log.info(e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void updateUser(User user) {
        while (true) {
            try {
                System.out.println("1.Изменить email");
                System.out.println("2.Изменить name");
                System.out.println("3.Изменить age");
                System.out.println("4.Сохранить");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        user.setEmail(scanner.next());
                        break;
                    case 2:
                        user.setName(scanner.next());
                        break;
                    case 3:
                        user.setAge(scanner.nextInt());
                        break;
                    case 4:
                        crudOperation.updateUser(user);
                        break;
                    default:
                        System.out.println("Такой операции нету");
                }
            } catch (MyException e) {
                System.out.println("Произошла ошикба");
                log.info(e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void workUser(User user) {
        int choice;
        while (true) {
            try {
                System.out.println("Ваш User: " + user.toString());
                System.out.println("1.Изменить user");
                System.out.println("2.Удалить user");
                System.out.println("3.Выйти");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        updateUser(user);
                        System.out.println("User обнавлен");
                        return;
                    case 2:
                        crudOperation.deleteUser(user);
                        System.out.println("User удален");
                        return;
                    case 3:
                        return;
                    default:
                        System.out.println("Такой операции нету");
                }
            } catch (MyException e) {
                System.out.println("Произошла ошикба");
                log.info(e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void createUser() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ведите имя");
            String username = scanner.next();
            System.out.println("Ведите email");
            String email = scanner.next();
            System.out.println("Ведите возраст");
            int age = scanner.nextInt();
            var userDto = UserDto.builder()
                    .email(email)
                    .age(age)
                    .name(username)
                    .build();
            crudOperation.saveUser(userDto);
            System.out.println("User сохранен");
        } catch (MyException e) {
            System.out.println("Произошла ошикба");
            log.info(e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка");
        }
    }
}
