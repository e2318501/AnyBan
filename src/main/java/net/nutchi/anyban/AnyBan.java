package net.nutchi.anyban;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.nutchi.anyban.command.*;
import net.nutchi.anyban.listener.LoginListener;
import net.nutchi.anyban.model.BannedIp;
import net.nutchi.anyban.model.BannedPlayer;
import net.nutchi.anyban.model.CachedPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public final class AnyBan extends Plugin {
    private final List<BannedPlayer> bannedPlayers = new ArrayList<>();
    private final List<BannedIp> bannedIps = new ArrayList<>();
    private final List<CachedPlayer> cachedPlayers = new ArrayList<>();
    private final File bannedPlayersFile = new File(getDataFolder(), "banned-players.json");
    private final File bannedIpsFile = new File(getDataFolder(), "banned-ips.json");
    private final File cachedPlayersFile = new File(getDataFolder(), "usercache.json");

    @Override
    public void onEnable() {
        register();
        loadBannedPlayers();
        loadBannedIps();
        loadCachedPlayers();
        getDataFolder().mkdir();
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

    private void loadBannedPlayers() {
        try {
            if (bannedPlayersFile.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                bannedPlayers.addAll(mapper.readValue(bannedPlayersFile, new TypeReference<List<BannedPlayer>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBannedIps() {
        try {
            if (bannedIpsFile.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                bannedIps.addAll(mapper.readValue(bannedIpsFile, new TypeReference<List<BannedIp>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCachedPlayers() {
        try {
            if (cachedPlayersFile.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                cachedPlayers.addAll(mapper.readValue(cachedPlayersFile, new TypeReference<List<CachedPlayer>>() {
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBannedPlayersAsync() {
        getProxy().getScheduler().runAsync(this, this::saveBannedPlayers);
    }

    private void saveBannedPlayers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(bannedPlayersFile, bannedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBannedIpsAsync() {
        getProxy().getScheduler().runAsync(this, this::saveBannedIps);
    }

    private void saveBannedIps() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(bannedIpsFile, bannedIps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCachedPlayersAsync() {
        getProxy().getScheduler().runAsync(this, this::saveCachedPlayers);
    }

    private void saveCachedPlayers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(cachedPlayersFile, cachedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
