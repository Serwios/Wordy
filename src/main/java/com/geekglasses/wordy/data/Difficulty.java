package com.geekglasses.wordy.data;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public enum Difficulty {
    C1_C2("C1-C2");

    private final String value;
    private static final Map<String, Difficulty> difficultyMap =
            Arrays.stream(values()).collect(Collectors.toMap(Difficulty::getValue, difficulty -> difficulty));

    Difficulty(String value) {
        this.value = value;
    }

    public static Difficulty getByValue(String value) {
        return difficultyMap.get(value);
    }
}
