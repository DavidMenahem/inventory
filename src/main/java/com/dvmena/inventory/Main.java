package com.dvmena.inventory;
import com.dvmena.inventory.app.MainApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class,args);
    }
}