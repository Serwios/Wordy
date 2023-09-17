package com.geekglasses.wordy.data;

import java.io.*;

public class UserDataLoader {
    private final String FILE_PATH = "src/main/resources/com/geekglasses/wordy/userData.txt";
    public UserData read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String userLanguageLine = reader.readLine();
        String difficultyLine = reader.readLine();
        reader.close();

        String userLanguage = userLanguageLine.split("=")[1];
        String difficultyValue = difficultyLine.split("value=")[1].replaceAll("[\\(\\)]", "");

        Difficulty targetLanguageDifficulty = Difficulty.getByValue(difficultyValue);

        return new UserData(userLanguage, targetLanguageDifficulty);
    }

    public void writeDataInFile(UserData userData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("UserLanguage=" + userData.userLanguage());
            writer.newLine();
            writer.write("Difficulty=" + userData.targetLanguageDifficulty());
            writer.flush();
            writer.close();

            System.out.println("Strings were written to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
