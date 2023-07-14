package net.nutchi.anyban.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.model.CachedPlayer;
import net.nutchi.anyban.util.DateManager;

import java.net.InetSocketAddress;
import java.util.UUID;

@RequiredArgsConstructor
public class LoginListener implements Listener {
    private final AnyBan plugin;

    @EventHandler
    public void onLogin(LoginEvent event) {
        String name = event.getConnection().getName();
        UUID uuid = event.getConnection().getUniqueId();
        String ip = ((InetSocketAddress) event.getConnection().getSocketAddress()).getAddress().getHostAddress();

        plugin.getBannedPlayers().stream().filter(p -> p.getUuid().equals(uuid)).findAny().ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent("You are banned from this server.\nReason: " + p.getReason()));
        });

        plugin.getBannedIps().stream().filter(p -> p.getIp().equals(ip)).findAny().ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent("Your IP address is banned from this server.\nReason: " + p.getReason()));
        });

        plugin.getCachedPlayers().removeIf(p -> DateManager.hasExpired(p.getExpiresOn()));

        if (!event.isCancelled()) {
            plugin.getCachedPlayers().removeIf(p -> p.getUuid().equals(uuid));
            plugin.getCachedPlayers().add(new CachedPlayer(name, uuid, DateManager.getAMonthLater()));
        }

        plugin.saveCachedPlayersAsync();
    }
}
