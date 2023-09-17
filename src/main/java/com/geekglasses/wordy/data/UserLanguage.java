package com.geekglasses.wordy.data;

import lombok.Getter;

@Getter
public enum UserLanguage {
    UKRAINIAN("Ukrainian", "uk");
//
//    // Not implemented
//    FRENCH("French", "fr"),
//
//    // Not implemented
//    GERMAN("German", "de");

    private final String name;
    private final String code;

    UserLanguage(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
