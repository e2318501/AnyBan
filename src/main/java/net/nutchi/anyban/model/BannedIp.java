package net.nutchi.anyban.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@RequiredArgsConstructor
public class BannedIp {
    private final String ip;
    private final String created;
    private final String source;
    private final String expires;
    private final String reason;
}
