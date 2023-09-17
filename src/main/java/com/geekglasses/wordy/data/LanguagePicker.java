package com.geekglasses.wordy.data;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LanguagePicker extends Application {
    private Stage primaryStage;
    private StackPane root;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Language Selector App");

        root = new StackPane();
        Scene scene = new Scene(root, 400, 300);

        createStartScene();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createStartScene() {
        root.getChildren().clear();

        Label textLabel = new Label("Welcome to the Language Selector App!");
        textLabel.setFont(Font.font(20));
        root.getChildren().add(textLabel);

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> createLanguageScene());
        root.getChildren().add(startButton);

        // Text animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(textLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(2), new KeyValue(textLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(3), event -> textLabel.setText("Select your preferred language"))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void createLanguageScene() {
        root.getChildren().clear();

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("Ukrainian", "French", "German");
        languageComboBox.setPromptText("Select a language");
        root.getChildren().add(languageComboBox);

        ComboBox<String> levelComboBox = new ComboBox<>();
        levelComboBox.getItems().addAll("C1", "C2", "A1", "A2", "B1", "B2");
        levelComboBox.setPromptText("Select a level");
        root.getChildren().add(levelComboBox);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> processSelection(languageComboBox.getValue(), levelComboBox.getValue()));
        root.getChildren().add(submitButton);
    }

    private void processSelection(String language, String level) {
        // Process the selected language and level
        // For this example, let's just print the selected values
        System.out.println("Selected language: " + language);
        System.out.println("Selected level: " + level);

        // You can add further logic here based on the selected values

        // Assuming you want to go back to the start scene after processing the selection
        createStartScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
