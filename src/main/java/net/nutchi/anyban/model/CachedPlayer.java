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
public class CachedPlayer {
    private final String name;
    private final UUID uuid;
    private final String expiresOn;
}
