package com.github.e2318501.anyban;


import lombok.RequiredArgsConstructor;
import com.github.e2318501.anyban.util.DateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CachedPlayerManager {
    private final AnyBan plugin;
    private final List<CachedPlayer> cachedPlayers = new ArrayList<>();
    private static final String JSON_FILE_NAME = "usercache.json";

    public Optional<UUID> getUuid(String name) {
        return cachedPlayers.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .map(CachedPlayer::getUuid)
                .findAny();
    }

    public List<String> getNames() {
        return cachedPlayers.stream().map(CachedPlayer::getName).collect(Collectors.toList());
    }

    public void renew(String name, UUID uuid) {
        cachedPlayers.removeIf(p -> p.getUuid().equals(uuid));
        cachedPlayers.add(new CachedPlayer(name, uuid, DateManager.getAMonthLater()));
        save();
    }

    public void removeExpired() {
        cachedPlayers.removeIf(p -> DateManager.hasExpired(p.getExpiresOn()));
        save();
    }

    public void load() {
        this.cachedPlayers.clear();
        this.cachedPlayers.addAll(plugin.getStorage().load(CachedPlayer[].class, JSON_FILE_NAME));
    }

    public void save() {
        plugin.getStorage().saveAsync(cachedPlayers, JSON_FILE_NAME);
    }
}
