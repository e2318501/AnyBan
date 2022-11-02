package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getAddedBannedIpList
import com.github.tsuoihito.anyban.handlers.getPlayersCandidate
import com.github.tsuoihito.anyban.handlers.getUserIp
import com.github.tsuoihito.anyban.utils.calcCurrentTimeString
import com.github.tsuoihito.anyban.utils.getMessage
import com.github.tsuoihito.anyban.utils.kickPlayer
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.util.concurrent.CompletableFuture

class BanIpCommand(private val plugin: AnyBan) : Command("aban-ip", "anyban.command.ban-ip"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
            return
        }
        val name = args[0]
        val reason = if (args.size > 1) args[1] else plugin.config?.getString("defaultBanReason").orEmpty()

        val ips = getUserIp(plugin.userIpList, name)?.ips
        if (ips == null) {
            sender.sendMessage(getMessage(plugin.config, "message.ipNotFound") { it.replace("%name%", args[0]) })
            return
        }
        ips.forEach {
            plugin.bannedIpList =
                getAddedBannedIpList(plugin.bannedIpList, it, calcCurrentTimeString(), sender.name, "forever", reason)
        }

        sender.sendMessage(getMessage(plugin.config, "message.banSuccess") { it.replace("%name%", name) })
        kickPlayer(
            plugin.proxy,
            name,
            getMessage(plugin.config, "message.youAreBanned", false) { it.replace("%reason%", reason) })

        CompletableFuture.runAsync { plugin.saveData() }
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): Iterable<String> {
        return if (args.size == 1) {
            getPlayersCandidate(plugin.userUuidList, args[0])
        } else listOf()
    }
}
