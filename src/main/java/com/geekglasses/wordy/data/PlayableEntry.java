package com.geekglasses.wordy.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PlayableEntry {
    private final String targetWord;
    private final String correctGuess;
    private final String option2;
    private final String option3;
    private final String option4;
}
