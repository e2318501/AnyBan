package net.nutchi.anyban.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.nutchi.anyban.AnyBan;
import net.nutchi.anyban.util.Message;
import net.nutchi.anyban.util.TabCompleteUtil;

import java.util.Collections;

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

            if (plugin.getBannedPlayerManager().isBanned(name)) {
                plugin.getBannedPlayerManager().remove(name);

                sender.sendMessage(Message.UNBAN_COMMAND.component(name));
            } else {
                sender.sendMessage(Message.IS_NOT_BANNED.component());
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
            return TabCompleteUtil.filter(plugin.getBannedPlayerManager().getNames(), args[0]);
        } else {
            return Collections.emptyList();
        }
    }
}
