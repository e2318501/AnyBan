package net.nutchi.anyban;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import net.nutchi.anyban.util.DateManager;
import net.nutchi.anyban.util.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BannedPlayerManager {
    private final AnyBan plugin;
    private final List<BannedPlayer> bannedPlayers = new ArrayList<>();
    private static final String JSON_FILE_NAME = "banned-players.json";

    public boolean isBanned(String name) {
        return bannedPlayers.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }

    public void add(UUID uuid, String name, String source, String reason) {
        bannedPlayers.add(new BannedPlayer(uuid, name, DateManager.getCurrent(), source, Message.DEFAULT_EXPIRES.text(), reason));
        save();
    }

    public void remove(String name) {
        bannedPlayers.removeIf(p -> p.getName().equalsIgnoreCase(name));
        save();
    }

    public List<String> getNames() {
        return bannedPlayers.stream().map(BannedPlayer::getName).collect(Collectors.toList());
    }

    public Optional<BannedPlayer> getBannedPlayer(UUID uuid) {
        return bannedPlayers.stream().filter(p -> p.getUuid().equals(uuid)).findAny();
    }

    public List<TextComponent> getDescriptions() {
        return bannedPlayers.stream()
                .map(p -> Message.BAN_DESCRIPTION.component(p.getName(), p.getSource(), p.getReason()))
                .collect(Collectors.toList());
    }

    public void load() {
        bannedPlayers.clear();
        bannedPlayers.addAll(plugin.getStorage().load(BannedPlayer[].class, JSON_FILE_NAME));
    }

    public void save() {
        plugin.getStorage().saveAsync(bannedPlayers, JSON_FILE_NAME, true);
    }
}
