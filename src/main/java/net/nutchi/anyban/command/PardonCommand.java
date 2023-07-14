package net.nutchi.anyban.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.model.BannedPlayer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PardonCommand extends Command implements TabExecutor {
    private final AnyBan plugin;

    public PardonCommand(AnyBan plugin) {
        super("apardon", "anyban.command.pardon");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String name = args[0];

            if (plugin.getBannedPlayers().stream().anyMatch(p -> p.getName().equalsIgnoreCase(name))) {
                plugin.getBannedPlayers().removeIf(p -> p.getName().equalsIgnoreCase(name));
                plugin.saveBannedPlayersAsync();

                sender.sendMessage(new TextComponent("Unbanned " + name));
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Nothing changed. The player isn't banned"));
            }
        } else if (args.length < 1) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incomplete command"));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incorrect argument for command"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return plugin.getBannedPlayers().stream().map(BannedPlayer::getName).filter(n -> n.startsWith(args[0])).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
