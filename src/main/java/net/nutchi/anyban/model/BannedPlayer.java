package net.nutchi.anyban.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@Jacksonized
@RequiredArgsConstructor
public class BannedPlayer {
    private final UUID uuid;
    private final String name;
    private final String created;
    private final String source;
    private final String expires;
    private final String reason;
}
