package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getBannedIp
import com.github.tsuoihito.anyban.handlers.getPlayersCandidate
import com.github.tsuoihito.anyban.utils.getMessage
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class IsBanIpCommand(private val plugin: AnyBan) : Command("aisban-ip", "anyban.command.isban-ip"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
        }
        val name = args[0]

        val info = getBannedIp(plugin.bannedIpList, plugin.userIpList, name)
        if (info == null) {
            sender.sendMessage(getMessage(plugin.config, "message.isNotBanned") { it.replace("%name%", name) })
            return
        }
        sender.sendMessage(
            getMessage(plugin.config, "message.isBanned") {
                it
                    .replace("%name%", name)
                    .replace("%reason%", info.reason)
                    .replace("%time%", info.created)
                    .replace("%source%", info.source)
            }
        )
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): Iterable<String> {
        return if (args.size == 1) {
            getPlayersCandidate(plugin.userUuidList, args[0])
        } else listOf()
    }
}
