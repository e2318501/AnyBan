package net.nutchi.anyban.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.model.BannedPlayer;
import net.nutchi.anyban.model.CachedPlayer;
import net.nutchi.anyban.util.DateManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BanCommand extends Command implements TabExecutor {
    private final AnyBan plugin;

    public BanCommand(AnyBan plugin) {
        super("aban", "anyban.command.ban");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String name = args[0];
            String reason;
            if (args.length == 1) {
                reason = "Banned by an operator.";
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }
            String source = sender instanceof ProxiedPlayer ? sender.getName() : "Server";

            Optional<CachedPlayer> cachedPlayer = plugin.getCachedPlayers().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findAny();
            if (cachedPlayer.isPresent()) {
                if (plugin.getBannedPlayers().stream().noneMatch(p -> p.getName().equalsIgnoreCase(name))) {
                    plugin.getBannedPlayers().add(new BannedPlayer(cachedPlayer.get().getUuid(), name, DateManager.getCurrent(), source, "forever", reason));
                    plugin.saveBannedPlayersAsync();

                    sender.sendMessage(new TextComponent("Banned " + name + ": " + reason));

                    ProxiedPlayer proxiedPlayer = plugin.getProxy().getPlayer(name);
                    if (proxiedPlayer != null) {
                        proxiedPlayer.disconnect(new TextComponent("You are banned from this server"));
                    }
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Nothing changed. The player is already banned"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "That player does not exist"));
            }
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incomplete command"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return plugin.getCachedPlayers().stream().map(CachedPlayer::getName).filter(n -> n.startsWith(args[0])).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
