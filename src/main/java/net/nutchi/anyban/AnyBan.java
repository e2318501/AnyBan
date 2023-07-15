package net.nutchi.anyban;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.nutchi.anyban.command.*;
import net.nutchi.anyban.listener.LoginListener;
import net.nutchi.anyban.model.BannedIp;
import net.nutchi.anyban.model.BannedPlayer;
import net.nutchi.anyban.model.CachedPlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public final class AnyBan extends Plugin {
    private final List<BannedPlayer> bannedPlayers = new ArrayList<>();
    private final List<BannedIp> bannedIps = new ArrayList<>();
    private final List<CachedPlayer> cachedPlayers = new ArrayList<>();

    private final File bannedPlayersFile = new File(getDataFolder(), "banned-players.json");
    private final File bannedIpsFile = new File(getDataFolder(), "banned-ips.json");
    private final File cachedPlayersFile = new File(getDataFolder(), "usercache.json");

    private final Gson gson = new Gson();
    private final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

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
        if (bannedPlayersFile.exists()) {
            try (Reader reader = new FileReader(bannedPlayersFile)) {
                bannedPlayers.clear();
                bannedPlayers.addAll(Arrays.asList(gson.fromJson(reader, BannedPlayer[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBannedIps() {
        if (bannedIpsFile.exists()) {
            try (Reader reader = new FileReader(bannedIpsFile)) {
                bannedIps.clear();
                bannedIps.addAll(Arrays.asList(gson.fromJson(reader, BannedIp[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCachedPlayers() {
        if (cachedPlayersFile.exists()) {
            try (Reader reader = new FileReader(cachedPlayersFile)) {
                cachedPlayers.clear();
                cachedPlayers.addAll(Arrays.asList(gson.fromJson(reader, CachedPlayer[].class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveBannedPlayersAsync() {
        getProxy().getScheduler().runAsync(this, this::saveBannedPlayers);
    }

    private void saveBannedPlayers() {
        try (Writer writer = new FileWriter(bannedPlayersFile)) {
            prettyGson.toJson(bannedPlayers.toArray(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBannedIpsAsync() {
        getProxy().getScheduler().runAsync(this, this::saveBannedIps);
    }

    private void saveBannedIps() {
        try (Writer writer = new FileWriter(bannedIpsFile)) {
            prettyGson.toJson(bannedIps.toArray(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCachedPlayersAsync() {
        getProxy().getScheduler().runAsync(this, this::saveCachedPlayers);
    }

    private void saveCachedPlayers() {
        try (Writer writer = new FileWriter(cachedPlayersFile)) {
            gson.toJson(cachedPlayers.toArray(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
