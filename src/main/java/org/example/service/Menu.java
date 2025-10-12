package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.mistake.MyMistake;
import org.example.repository.CRUD_Operation;
import org.example.repository.CRUD_OperationImpl;

import java.util.Scanner;

@Slf4j
public class Menu {

    private CRUD_Operation crudOperation = CRUD_OperationImpl.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int choice;
        while (true) {
            System.out.println("Выберите оперцию");
            System.out.println("1.Создать user");
            System.out.println("2.Найти user");
            System.out.println("3.Выход");
            choice=scanner.nextInt();
            if(choice==1){
                createUser();
            }else if(choice==2){
                findUser();
            }else if(choice==3){
                break;
            }
            else {
                System.out.println("Такой операции нету");
            }
        }
    }

    private void findUser(){
        int choice;
        User user = null;
        while (true) {
            try {
                System.out.println("Как найти user");
                System.out.println("1.По email");
                System.out.println("2.По id");
                choice =scanner.nextInt();
                if(choice==1){
                    System.out.println("Ведите email");
                    String email = scanner.next();
                    user = crudOperation.findUserByEmail(email);
                    workUser(user);
                    break;
                }else if(choice==2){
                    System.out.println("Ведите id");
                    int id = scanner.nextInt();
                    user = crudOperation.findUserById(id);
                    workUser(user);
                    break;
                }else {
                    System.out.println("Такой операции нету");
                }
            }catch (MyMistake e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void updateUser(User user){
        while (true) {
            try {
                System.out.println("1.Изменить email");
                System.out.println("2.Изменить name");
                System.out.println("3.Изменить age");
                System.out.println("4.Сохранить");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if(choice==1){
                    user.setEmail(scanner.next());
                }else if(choice==2){
                    user.setName(scanner.next());
                }else if(choice==3){
                    user.setAge(scanner.nextInt());
                }else if(choice==4){
                    crudOperation.updateUser(user);
                    break;
                }else {
                    System.out.println("Такой операции нету");
                }
            }catch (MyMistake e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void workUser(User user){
        int choice;
        while(true) {
            try {
                System.out.println("Ваш User: " + user.toString());
                System.out.println("1.Изменить user");
                System.out.println("2.Удалить user");
                System.out.println("3.Выйти");
                choice = scanner.nextInt();
                if (choice == 1) {
                    updateUser(user);
                    System.out.println("User обнавлен");
                    break;
                } else if (choice == 2) {
                    crudOperation.deleteUser(user);
                    System.out.println("User удален");
                    break;
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Такой операции нету");
                }
            }catch (MyMistake e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("Произошла ошибка");
            }
        }
    }

    private void createUser(){
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
        }catch (MyMistake e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println("Произошла ошибка");
        }
    }

}
