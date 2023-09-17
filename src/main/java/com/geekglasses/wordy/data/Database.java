package com.geekglasses.wordy.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
public class Database {
    private final int BATCH_SIZE = 30;

    // Can be in DB - main point to get different batches each time
    private List<Word> allEnglishWordsInCache = null;

    public List<Word> extractNElements() {
        if (allEnglishWordsInCache.size() >= BATCH_SIZE) {
            List<Word> randomWords = getRandomNElements();

            for (Word word : randomWords) {
                allEnglishWordsInCache.remove(word);
            }

            System.out.println("Extracted random words from DB");

            return randomWords;
        } else {
            System.out.println("Not enough words in the map to pick 30.");
            return null;
        }
    }

    private List<Word> getRandomNElements() {
        if (BATCH_SIZE > allEnglishWordsInCache.size()) {
            throw new IllegalArgumentException("Cannot select more elements than available in the list.");
        }

        List<Word> result = new ArrayList<>();
        Random random = new Random();

        while (result.size() < BATCH_SIZE) {
            int randomIndex = random.nextInt(allEnglishWordsInCache.size());
            Word randomElement = allEnglishWordsInCache.get(randomIndex);
            result.add(randomElement);
            allEnglishWordsInCache.remove(randomIndex);
        }

        return result;
    }
}
