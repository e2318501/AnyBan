package net.nutchi.anyban.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.util.Message;
import net.nutchi.anyban.util.IpChecker;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collections;

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
            String reason = args.length == 1 ? Message.DEFAULT_BAN_REASON.text() : String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            String source = sender instanceof ProxiedPlayer ? sender.getName() : Message.CONSOLE_SOURCE.text();

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
                if (!plugin.getBannedIpManager().isBanned(ip)) {
                    plugin.getBannedIpManager().add(ip, source, reason);

                    sender.sendMessage(Message.BAN_IP_COMMAND.component(ip, reason));

                    plugin.getProxy().getPlayers().forEach(p -> {
                        String pIp = ((InetSocketAddress) p.getSocketAddress()).getAddress().getHostAddress();
                        if (pIp.equals(ip)) {
                            p.disconnect(Message.YOU_ARE_BANNED_IP.component(reason));
                        }
                    });
                } else {
                    sender.sendMessage(Message.IP_ALREADY_BANNED.component());
                }
            } else {
                sender.sendMessage(Message.INVALID_IP.component());
            }
        } else {
            sender.sendMessage(Message.INCOMPLETE_COMMAND.component());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
