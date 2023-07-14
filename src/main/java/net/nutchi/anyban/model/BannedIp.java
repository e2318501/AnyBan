package net.nutchi.anyban.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BannedIp {
    private final String ip;
    private final String created;
    private final String source;
    private final String expires;
    private final String reason;
}
