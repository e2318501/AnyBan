package com.github.e2318501.anyban;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import com.github.e2318501.anyban.util.DateManager;
import com.github.e2318501.anyban.util.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BannedIpManager {
    private final AnyBan plugin;
    private final List<BannedIp> bannedIps = new ArrayList<>();
    private static final String JSON_FILE_NAME = "banned-ips.json";

    public boolean isBanned(String ip) {
        return bannedIps.stream().anyMatch(p -> p.getIp().equals(ip));
    }

    public void add(String ip, String source, String reason) {
        bannedIps.add(new BannedIp(ip, DateManager.getCurrent(), source, Message.DEFAULT_EXPIRES.text(), reason));
        save();
    }

    public void remove(String ip) {
        bannedIps.removeIf(p -> p.getIp().equals(ip));
        save();
    }

    public List<String> getIps() {
        return bannedIps.stream().map(BannedIp::getIp).collect(Collectors.toList());
    }

    public Optional<BannedIp> getBannedIp(String ip) {
        return bannedIps.stream().filter(p -> p.getIp().equals(ip)).findAny();
    }

    public List<TextComponent> getDescriptions() {
        return bannedIps.stream()
                .map(p -> Message.BAN_DESCRIPTION.component(p.getIp(), p.getSource(), p.getReason()))
                .collect(Collectors.toList());
    }

    public void load() {
        bannedIps.clear();
        bannedIps.addAll(plugin.getStorage().load(BannedIp[].class, JSON_FILE_NAME));
    }

    public void save() {
        plugin.getStorage().saveAsync(bannedIps, JSON_FILE_NAME, true);
    }
}
