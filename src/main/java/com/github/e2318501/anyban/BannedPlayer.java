package com.github.e2318501.anyban;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BannedPlayer {
    private final UUID uuid;
    private final String name;
    private final String created;
    private final String source;
    private final String expires;
    private final String reason;
}
