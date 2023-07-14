package net.nutchi.anyban.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.model.BannedIp;
import net.nutchi.anyban.util.IpChecker;
import net.nutchi.anyban.util.DateManager;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class BanIpCommand extends Command implements TabExecutor {
    private final AnyBan plugin;

    public BanIpCommand(AnyBan plugin) {
        super("aban-ip", "anyban.command.ban-ip");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String target = args[0];
            String reason;
            if (args.length == 1) {
                reason = "Banned by an operator.";
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }
            String source = sender instanceof ProxiedPlayer ? sender.getName() : "Server";

            ProxiedPlayer proxiedPlayer = plugin.getProxy().getPlayer(target);

            String ip;
            if (IpChecker.isIp(target)) {
                ip = target;
            } else if (proxiedPlayer != null) {
                ip = ((InetSocketAddress) proxiedPlayer.getSocketAddress()).getAddress().getHostAddress();
            } else {
                ip = null;
            }

            if (ip != null) {
                if (plugin.getBannedIps().stream().noneMatch(p -> p.getIp().equals(ip))) {
                    plugin.getBannedIps().add(new BannedIp(ip, DateManager.getCurrent(), source, "forever", reason));
                    plugin.saveBannedIpsAsync();

                    sender.sendMessage(new TextComponent("Banned IP " + ip + ": " + reason));

                    plugin.getProxy().getPlayers().forEach(p -> {
                        String pIp = ((InetSocketAddress) p.getSocketAddress()).getAddress().getHostAddress();
                        if (pIp.equals(ip)) {
                            p.disconnect(new TextComponent("You have been IP banned from this server"));
                        }
                    });
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Nothing changed. That IP is already banned"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid IP address or unknown player"));
            }
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incomplete command"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
