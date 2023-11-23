package net.nutchi.anyban;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.nutchi.anyban.command.*;
import net.nutchi.anyban.listener.LoginListener;

@Getter
public final class AnyBan extends Plugin {
    private final BannedPlayerManager bannedPlayerManager = new BannedPlayerManager(this);
    private final BannedIpManager bannedIpManager = new BannedIpManager(this);
    private final CachedPlayerManager cachedPlayerManager = new CachedPlayerManager(this);
    private final Storage storage = new Storage(this);

    @Override
    public void onEnable() {
        register();

        getDataFolder().mkdir();

        bannedPlayerManager.load();
        bannedIpManager.load();
        cachedPlayerManager.load();
    }

    private void register() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new LoginListener(this));
        pm.registerCommand(this, new BanCommand(this));
        pm.registerCommand(this, new BanIpCommand(this));
        pm.registerCommand(this, new BanListCommand(this));
        pm.registerCommand(this, new PardonCommand(this));
        pm.registerCommand(this, new PardonIpCommand(this));
    }
}
