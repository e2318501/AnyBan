package com.github.tsuoihito.anyban.utils

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.config.Configuration

val prefix = ChatColor.BLUE.toString() + "[AnyBan] " + ChatColor.RESET.toString()

fun getMessage(
    config: Configuration?,
    path: String,
    prefixEnabled: Boolean = true,
    callback: ((String) -> String)? = null
): TextComponent {
    return TextComponent(
        ChatColor.translateAlternateColorCodes('&',
            config?.getString(path)
                ?.let { if (prefixEnabled) prefix + it else it }
                ?.let { if (callback != null) callback(it) else it }
                .orEmpty()
        ))
}
