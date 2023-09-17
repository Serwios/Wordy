package com.geekglasses.wordy;

import com.geekglasses.wordy.controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordyApplication extends Application {
    public static String APPLICATION_NAME = "Wordy!";

    @Override
    public void start(Stage stage) throws IOException {
        MenuController menuController = new MenuController();
        FXMLLoader fxmlLoader = new FXMLLoader(WordyApplication.class.getResource("menu.fxml"));
        fxmlLoader.setController(menuController);

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setResizable(false);
        stage.setTitle(APPLICATION_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}