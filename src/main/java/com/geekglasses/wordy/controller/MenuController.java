package com.geekglasses.wordy.controller;

import com.geekglasses.wordy.WordyApplication;
import com.geekglasses.wordy.data.Difficulty;
import com.geekglasses.wordy.data.UserData;
import com.geekglasses.wordy.data.UserDataLoader;
import com.geekglasses.wordy.data.UserLanguage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.geekglasses.wordy.WordyApplication.APPLICATION_NAME;

public class MenuController {
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    @FXML
    private ChoiceBox<String> userLangChoiceBox;
    @FXML
    private Button playButton;

    @FXML
    public void initialize() {
        initializeChoiceBoxes();
        setChoiceBoxDefaultValues();
        setChoiceBoxEventHandlers();
        setPlayButtonAction();
    }

    private void initializeChoiceBoxes() {
        List<String> difficultyValues = Arrays.stream(Difficulty.values()).map(Difficulty::getValue).toList();
        List<String> userLangNames = Arrays.stream(UserLanguage.values()).map(UserLanguage::getName).toList();

        difficultyChoiceBox.getItems().addAll(difficultyValues);
        userLangChoiceBox.getItems().addAll(userLangNames);
    }

    private void setChoiceBoxDefaultValues() {
        difficultyChoiceBox.setValue(Difficulty.C1_C2.getValue());
        userLangChoiceBox.setValue(UserLanguage.UKRAINIAN.getName());
    }

    private void setChoiceBoxEventHandlers() {
        difficultyChoiceBox.setOnAction(event -> {
            String selectedOption = difficultyChoiceBox.getValue();
            System.out.println("Selected difficulty: " + selectedOption);
        });

        userLangChoiceBox.setOnAction(event -> {
            String selectedOption = userLangChoiceBox.getValue();
            System.out.println("Selected language: " + selectedOption);
        });
    }

    private void setPlayButtonAction() {
        playButton.setOnAction(actionEvent -> {
            UserData userData = resolveUserData();
            UserDataLoader userDataLoader = new UserDataLoader();
            userDataLoader.writeDataInFile(userData);

            closeMenuStageAndOpenGameStage();
        });
    }

    private UserData resolveUserData() {
        return new UserData(userLangChoiceBox.getValue(), Difficulty.getByValue(difficultyChoiceBox.getValue()));
    }

    private void closeMenuStageAndOpenGameStage() {
        Stage menuStage = (Stage) playButton.getScene().getWindow();
        menuStage.close();

        GameController gameController = new GameController();
        FXMLLoader fxmlLoader = new FXMLLoader(WordyApplication.class.getResource("game.fxml"));
        fxmlLoader.setController(gameController);

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage gameStage = new Stage();
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle(APPLICATION_NAME);
        gameStage.setScene(new Scene(root));
        gameStage.setResizable(false);
        gameStage.show();
    }
}
