package com.geekglasses.wordy.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PlayableEntryResponse {
    private final PlayableEntry playableEntry;
    private final boolean valid;
    private final Action status;
}
