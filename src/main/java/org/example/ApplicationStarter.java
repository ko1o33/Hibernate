package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.service.Menu;


@Slf4j
public class ApplicationStarter {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.menu();
    }

}
