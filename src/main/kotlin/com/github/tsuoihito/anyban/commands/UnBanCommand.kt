package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getBannedPlayersCandidate
import com.github.tsuoihito.anyban.handlers.getRemovedBannedPlayerList
import com.github.tsuoihito.anyban.utils.getMessage
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.util.concurrent.CompletableFuture

class UnBanCommand(private val plugin: AnyBan) : Command("aunban", "anyban.command.pardon", "apardon"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
            return
        }
        val name = args[0]
        plugin.bannedPlayerList = getRemovedBannedPlayerList(plugin.bannedPlayerList, name)
        sender.sendMessage(getMessage(plugin.config, "message.unBanSuccess") { it.replace("%name%", name) })

        CompletableFuture.runAsync { plugin.saveData() }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): Iterable<String> {
        return if (args.size == 1) {
            getBannedPlayersCandidate(plugin.bannedPlayerList, args[0])
        } else listOf()
    }
}
