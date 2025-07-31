package com.github.e2318501.anyban.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import com.github.e2318501.anyban.AnyBan;
import com.github.e2318501.anyban.util.Message;
import com.github.e2318501.anyban.util.TabCompleteUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BanListCommand extends Command implements TabExecutor {
    private final AnyBan plugin;
    private static final String PLAYERS = "players";
    private static final String IPS = "ips";

    public BanListCommand(AnyBan plugin) {
        super("abanlist", "anyban.command.banlist");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<TextComponent> playersDescriptions = plugin.getBannedPlayerManager().getDescriptions();
        List<TextComponent> ipsDescriptions = plugin.getBannedIpManager().getDescriptions();

        List<TextComponent> descriptions = new ArrayList<>();
        int count;

        if (args.length == 0) {
            count = playersDescriptions.size() + ipsDescriptions.size();
            descriptions.addAll(playersDescriptions);
            descriptions.addAll(ipsDescriptions);
        } else if (args.length == 1 && args[0].equals(PLAYERS)) {
            count = playersDescriptions.size();
            descriptions.addAll(playersDescriptions);
        } else if (args.length == 1 && args[0].equals(IPS)) {
            count = ipsDescriptions.size();
            descriptions.addAll(ipsDescriptions);
        } else {
            sender.sendMessage(Message.INCORRECT_ARGUMENT.component());
            return;
        }

        TextComponent header = count != 0 ? Message.BAN_LIST_HEADER_BANS.component(count) : Message.BAN_LIST_HEADER_NO_BANS.component();
        descriptions.add(0, header);

        TextComponent message = new TextComponent();
        for (int i = 0; i < descriptions.size(); i++) {
            message.addExtra(descriptions.get(i));
            if (i != descriptions.size() - 1) {
                message.addExtra("\n");
            }
        }

        sender.sendMessage(message);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return TabCompleteUtil.filter(Arrays.asList(PLAYERS, IPS), args[0]);
        } else {
            return Collections.emptyList();
        }
    }
}
