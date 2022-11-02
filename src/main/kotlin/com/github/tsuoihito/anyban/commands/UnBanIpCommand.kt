package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getPlayersCandidate
import com.github.tsuoihito.anyban.handlers.getRemovedBannedIpList
import com.github.tsuoihito.anyban.utils.getMessage
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.util.concurrent.CompletableFuture

class UnBanIpCommand(private val plugin: AnyBan) : Command("aunban-ip", "anyban.command.pardon-ip", "apardon-ip"),
    TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
            return
        }

        val name = args[0]
        plugin.bannedIpList = getRemovedBannedIpList(plugin.bannedIpList, plugin.userIpList, name)
        sender.sendMessage(getMessage(plugin.config, "message.unBanSuccess") { it.replace("%name%", name) })

        CompletableFuture.runAsync { plugin.saveData() }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): Iterable<String>? {
        return if (args.size == 1) {
            getPlayersCandidate(plugin.userUuidList, args[0])
        } else null
    }
}
