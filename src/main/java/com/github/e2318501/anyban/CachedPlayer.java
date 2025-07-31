package com.github.e2318501.anyban;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CachedPlayer {
    private final String name;
    private final UUID uuid;
    private final String expiresOn;
}
