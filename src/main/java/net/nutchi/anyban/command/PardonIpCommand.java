package net.nutchi.anyban.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.model.BannedIp;
import net.nutchi.anyban.util.IpChecker;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PardonIpCommand extends Command implements TabExecutor {
    private final AnyBan plugin;

    public PardonIpCommand(AnyBan plugin) {
        super("apardon-ip", "anyban.command.pardon-ip");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            String ip = args[0];

            if (IpChecker.isIp(ip)) {
                if (plugin.getBannedIps().stream().anyMatch(p -> p.getIp().equals(ip))) {
                    plugin.getBannedIps().removeIf(p -> p.getIp().equals(ip));
                    plugin.saveBannedIpsAsync();

                    sender.sendMessage(new TextComponent("Unbanned IP " + ip));
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Nothing changed. That IP isn't banned"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid IP address"));
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
            return plugin.getBannedIps().stream().map(BannedIp::getIp).filter(ip -> ip.startsWith(args[0])).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
