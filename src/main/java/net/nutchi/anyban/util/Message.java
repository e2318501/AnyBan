package net.nutchi.anyban.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public enum Message {
    BAN_REASON("You are banned from this server.\nReason: %s"),
    BAN_IP_REASON("Your IP address is banned from this server.\nReason: %s"),
    BAN_LIST_HEADER_BANS("There are %d ban(s):"),
    BAN_LIST_HEADER_NO_BANS("There are no bans"),
    BAN_DESCRIPTION("%s was banned by %s: %s"),
    BAN_COMMAND("Banned %s: %s"),
    BAN_IP_COMMAND("Banned IP %s: %s"),
    UNBAN_COMMAND("Unbanned %s"),
    UNBAN_IP_COMMAND("Unbanned IP %s"),
    YOU_ARE_BANNED("You are banned from this server.\nReason: %s"),
    YOU_ARE_BANNED_IP("Your IP address is banned from this server.\nReason: %s"),
    IS_NOT_BANNED(ChatColor.RED + "Nothing changed. The player isn't banned"),
    IS_NOT_BANNED_IP(ChatColor.RED + "Nothing changed. That IP isn't banned"),
    INVALID_IP(ChatColor.RED + "Invalid IP address"),
    PLAYER_NOT_FOUND(ChatColor.RED + "That player does not exist"),
    PLAYER_ALREADY_BANNED(ChatColor.RED + "Nothing changed. The player is already banned"),
    IP_ALREADY_BANNED(ChatColor.RED + "Nothing changed. That IP is already banned"),
    INCOMPLETE_COMMAND(ChatColor.RED + "Incomplete command"),
    INCORRECT_ARGUMENT(ChatColor.RED + "Incorrect argument for command"),
    DEFAULT_BAN_REASON("Banned by an operator."),
    DEFAULT_EXPIRES("forever"),
    CONSOLE_SOURCE("Server");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String text() {
        return message;
    }

    public TextComponent component(Object... args) {
        return new TextComponent(String.format(message, args));
    }
}
