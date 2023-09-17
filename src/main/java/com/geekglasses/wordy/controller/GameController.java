package com.geekglasses.wordy.controller;

import com.geekglasses.wordy.WordyApplication;
import com.geekglasses.wordy.data.*;
import com.geekglasses.wordy.game.Translator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.geekglasses.wordy.WordyApplication.APPLICATION_NAME;
import static com.geekglasses.wordy.game.DataUtil.initDataBaseWithEnglishWords;

public class GameController {
    public static Database database = new Database(initDataBaseWithEnglishWords());
    private PlayableEntry currentPlayableEntry;
    private List<Word> currentBatch;
    private UserData userData;

    @FXML
    private Button wordOption1Button;

    @FXML
    private Button wordOption2Button;

    @FXML
    private Button wordOption3Button;

    @FXML
    private Button wordOption4Button;

    @FXML
    private Text targetWord;

    public void init() throws IOException {
        UserDataLoader userDataLoader = new UserDataLoader();

        UserData readData = userDataLoader.read();
        if (readData == null) {
            throw new IllegalArgumentException("Data file is empty");
        } else {
            this.userData = readData;
        }

        this.currentBatch = database.extractNElements();
    }

    @FXML
    public void initialize() throws IOException {
        init();

        AtomicReference<Map<Word, String>> englishToUkrainianBatch = new AtomicReference<>(createTranslationMap(currentBatch));

        PlayableEntryResponse playableEntryResponse = getPlayableEntry(englishToUkrainianBatch.get());

        if (!playableEntryResponse.isValid()) {
            System.out.println("NON VALID PLAYABLE ENTRY. FIGURE OUT WHAT TO DO");
        }

        this.currentPlayableEntry = setNewPlayableEntry(englishToUkrainianBatch.get()).getPlayableEntry();

        setWordOptionButtonHandlers(englishToUkrainianBatch);
    }

    private void loadMenuScene() {
        Stage gameStage = (Stage) targetWord.getScene().getWindow();
        gameStage.close();

        MenuController menuController = new MenuController();
        FXMLLoader fxmlLoader = new FXMLLoader(WordyApplication.class.getResource("menu.fxml"));
        fxmlLoader.setController(menuController);

        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage menuStage = new Stage();
        menuStage.initModality(Modality.APPLICATION_MODAL);
        menuStage.setTitle(APPLICATION_NAME);
        menuStage.setScene(new Scene(root));
        menuStage.setResizable(false);
        menuStage.show();
    }

    public PlayableEntryResponse setNewPlayableEntry(Map<Word, String> englishToUkrainianBatch) {
        PlayableEntryResponse playableEntryResponse = getPlayableEntry(englishToUkrainianBatch);

        if (!playableEntryResponse.isValid()) {
            if (database.getAllEnglishWordsInCache().size() == 0) {
               System.out.println("You won");
               loadMenuScene();
            }

            this.currentBatch = database.extractNElements();
            System.out.println("DB Size: " + database.getAllEnglishWordsInCache().size());

            return new PlayableEntryResponse(playableEntryResponse.getPlayableEntry(), true, Action.REFRESH_BATCH);
        }

        PlayableEntry currentPlayableEntry = playableEntryResponse.getPlayableEntry();

        List<String> listOfWordsOption = getListOfWordsOption(currentPlayableEntry);

        targetWord.setText(currentPlayableEntry.getTargetWord());
        String option1 = getRandomWordForPlayableEntry(listOfWordsOption);
        wordOption1Button.setText(option1);
        listOfWordsOption.remove(option1);

        String option2 = getRandomWordForPlayableEntry(listOfWordsOption);
        wordOption2Button.setText(option2);
        listOfWordsOption.remove(option2);

        String option3 = getRandomWordForPlayableEntry(listOfWordsOption);
        wordOption3Button.setText(option3);
        listOfWordsOption.remove(option3);

        String option4 = getRandomWordForPlayableEntry(listOfWordsOption);
        wordOption4Button.setText(option4);

        return new PlayableEntryResponse(currentPlayableEntry, true, Action.NOTHING);
    }

    private static PlayableEntryResponse getPlayableEntry(Map<Word, String> wordMap) {
        Word randomWord = null;
        for (Word word : wordMap.keySet()) {
            if (!word.isUsed()) {
                randomWord = word;
            }
        }

        if (randomWord == null) {
            return new PlayableEntryResponse(null, false, Action.NOTHING);
        }

        String oldValue = wordMap.get(randomWord);
        wordMap.remove(randomWord);
        randomWord.setUsed(true);
        wordMap.put(randomWord, oldValue);

        List<Map.Entry<Word, String>> entries = new ArrayList<>(wordMap.entrySet());

        Collections.shuffle(entries);

        List<Map.Entry<Word, String>> selectedEntries = new ArrayList<>();

        for (Map.Entry<Word, String> entry : entries) {
            if (selectedEntries.size() < 3) {
                if (!entry.getKey().equals(randomWord)) {
                    selectedEntries.add(entry);
                }
            } else {
                break;
            }
        }

        return new PlayableEntryResponse(new PlayableEntry(
                randomWord.getValue(),
                wordMap.get(randomWord),
                selectedEntries.get(0).getValue(),
                selectedEntries.get(1).getValue(),
                selectedEntries.get(2).getValue()
        ), true, Action.NOTHING);
    }

    private static Map<Word, String> createTranslationMap(List<Word> words) {
        return words.stream()
                .collect(Collectors.toMap(
                        word -> word,
                        word -> Translator.translate(word.getValue())
                ));
    }

    private static String getRandomWordForPlayableEntry(List<String> listOfWordsOptions) {
        int randomValue = (int) (Math.random() * listOfWordsOptions.size());
        return listOfWordsOptions.get(randomValue);
    }

    private static List<String> getListOfWordsOption(PlayableEntry playableEntry) {
        return new ArrayList<>(Arrays.asList(playableEntry.getCorrectGuess(), playableEntry.getOption2(), playableEntry.getOption3(), playableEntry.getOption4()));
    }

    private void setWordOptionButtonHandlers(AtomicReference<Map<Word, String>> englishToUkrainianBatch) {
        List<Button> wordOptionButtons = Arrays.asList(wordOption1Button, wordOption2Button, wordOption3Button, wordOption4Button);

        for (Button button : wordOptionButtons) {
            button.setOnAction(event -> {
                if (button.getText().equals(currentPlayableEntry.getCorrectGuess())) {
                    PlayableEntryResponse playableEntryResponse1 = setNewPlayableEntry(englishToUkrainianBatch.get());

                    if (playableEntryResponse1.getStatus() == Action.REFRESH_BATCH) {
                        englishToUkrainianBatch.set(createTranslationMap(currentBatch));
                    } else {
                        this.currentPlayableEntry = setNewPlayableEntry(englishToUkrainianBatch.get()).getPlayableEntry();
                    }
                } else {
                    loadMenuScene();
                }
            });
        }
    }
}
