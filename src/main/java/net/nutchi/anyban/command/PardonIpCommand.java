package net.nutchi.anyban.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.util.Message;
import net.nutchi.anyban.util.IpChecker;
import net.nutchi.anyban.util.TabCompleteUtil;

import java.util.Collections;

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
                if (plugin.getBannedIpManager().isBanned(ip)) {
                    plugin.getBannedIpManager().remove(ip);

                    sender.sendMessage(Message.UNBAN_IP_COMMAND.component(ip));
                } else {
                    sender.sendMessage(Message.IS_NOT_BANNED_IP.component());
                }
            } else {
                sender.sendMessage(Message.INVALID_IP.component());
            }
        } else if (args.length < 1) {
            sender.sendMessage(Message.INCOMPLETE_COMMAND.component());
        } else {
            sender.sendMessage(Message.INCORRECT_ARGUMENT.component());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return TabCompleteUtil.filter(plugin.getBannedIpManager().getIps(), args[0]);
        } else {
            return Collections.emptyList();
        }
    }
}
