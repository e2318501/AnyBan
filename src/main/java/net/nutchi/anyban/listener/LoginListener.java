package net.nutchi.anyban.listener;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.util.Message;

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

        plugin.getBannedPlayerManager().getBannedPlayer(uuid).ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(Message.BAN_REASON.component(p.getReason()));
        });

        plugin.getBannedIpManager().getBannedIp(ip).ifPresent(p -> {
            event.setCancelled(true);
            event.setCancelReason(Message.BAN_IP_REASON.component(p.getReason()));
        });

        plugin.getCachedPlayerManager().removeExpired();

        if (!event.isCancelled()) {
            plugin.getCachedPlayerManager().renew(name, uuid);
        }
    }
}
