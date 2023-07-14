package net.nutchi.anyban.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BanListCommand extends Command implements TabExecutor {
    private final AnyBan plugin;

    public BanListCommand(AnyBan plugin) {
        super("abanlist", "anyban.command.banlist");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> playerDescriptions = plugin.getBannedPlayers().stream().map(p -> p.getName() + " was banned by " + p.getSource() + ": " + p.getReason()).collect(Collectors.toList());
        List<String> ipDescriptions = plugin.getBannedIps().stream().map(i -> i.getIp() + " was banned by " + i.getSource() + ": " + i.getReason()).collect(Collectors.toList());
        List<String> bannedDescriptions = Stream.of(playerDescriptions, ipDescriptions).flatMap(Collection::stream).collect(Collectors.toList());

        String playersInfo = String.join("\n", playerDescriptions);
        String ipsInfo = String.join("\n", ipDescriptions);
        String bannedInfo = String.join("\n", bannedDescriptions);

        if (args.length == 0) {
            String header = getHeader(playerDescriptions.size() + ipDescriptions.size());
            String message = header + bannedInfo;
            sender.sendMessage(new TextComponent(message));
        } else if (args.length == 1) {
            if (args[0].equals("players")) {
                String header = getHeader(playerDescriptions.size());
                String message = header + playersInfo;
                sender.sendMessage(new TextComponent(message));
            } else if (args[0].equals("ips")) {
                String header = getHeader(ipDescriptions.size());
                String message = header + ipsInfo;
                sender.sendMessage(new TextComponent(message));
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Incorrect argument for command"));
            }
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incorrect argument for command"));
        }
    }

    private String getHeader(int bans) {
        return bans == 0 ? "There are no bans" : "There are " + bans + " ban(s):\n";
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Stream.of("players", "ips").filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
