package com.geekglasses.wordy.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {
    private boolean used;
    private final String value;
    private final String level;
}
