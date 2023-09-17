package com.geekglasses.wordy.game;

import com.geekglasses.wordy.data.Word;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<Word> initDataBaseWithEnglishWords() {
        System.out.println("Started csv reading");
        String csvFilePath = "src/main/resources/com/geekglasses/wordy/octanove-vocabulary-profile-c1c2-1.0.csv";
        List<Word> englishWords = new ArrayList<>();

        try (FileReader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            System.out.println("Started DB init");;
            for (CSVRecord csvRecord : csvParser) {
                String word = csvRecord.get(0);
                String wordLevel = csvRecord.get(2);
                englishWords.add(new Word(false, word, wordLevel));
            }

            if (englishWords.size() % 30 != 0) {
                throw new IllegalArgumentException("Size of english words are incorrect. Should be (size % 30 == 0), but was: " + englishWords.size());
            }

            System.out.println("Finished csv reading");
            System.out.println("Finished DB init");

            return englishWords;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
