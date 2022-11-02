package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.utils.getMessage
import com.github.tsuoihito.anyban.utils.loadConfig
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class AdminCommand(private val plugin: AnyBan) : Command("anyBan", "anyban.admin"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
            return
        }
        when (args[0]) {
            "reload" -> {
                plugin.config = loadConfig(plugin)
                sender.sendMessage(getMessage(plugin.config, "message.configReloaded"))
                return
            }
        }
        sender.sendMessage(getMessage(plugin.config, "message.noSuchCommand"))
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): Iterable<String> {
        return if (args.size == 1) {
            listOf("reload")
                .filter { it.startsWith(args[0]) }
        } else listOf()
    }
}
