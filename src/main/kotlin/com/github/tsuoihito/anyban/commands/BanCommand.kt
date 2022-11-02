package com.github.tsuoihito.anyban.commands

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getAddedBannedPlayerList
import com.github.tsuoihito.anyban.handlers.getNewUserUuidList
import com.github.tsuoihito.anyban.handlers.getPlayersCandidate
import com.github.tsuoihito.anyban.handlers.getUuidFromCache
import com.github.tsuoihito.anyban.utils.*
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor
import java.util.concurrent.CompletableFuture

class BanCommand(private val plugin: AnyBan) : Command("aban", "anyban.command.ban"), TabExecutor {
    override fun execute(sender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            sender.sendMessage(getMessage(plugin.config, "message.invalidSyntax"))
            return
        }
        val name = args[0]
        val reason = if (args.size > 1) args[1] else plugin.config?.getString("defaultBanReason").orEmpty()

        val cachedUuid = getUuidFromCache(plugin.userUuidList, name)
        if (cachedUuid == null) {
            val fetchedUuid = fetchUuid(plugin.mapper, name, plugin.config?.getBoolean("floodgateEnabled") ?: false)
            if (fetchedUuid == null) {
                sender.sendMessage(getMessage(plugin.config, "message.invalidPlayerName") {
                    it.replace(
                        "%name%",
                        args[0]
                    )
                })
                return
            }
            plugin.userUuidList = getNewUserUuidList(
                plugin.userUuidList,
                fetchedUuid,
                name,
                calcAMonthLaterString()
            ) { calcIsExpired(it) }
            plugin.bannedPlayerList = getAddedBannedPlayerList(
                plugin.bannedPlayerList,
                fetchedUuid,
                name,
                calcCurrentTimeString(),
                sender.name,
                "forever",
                reason
            )
        }
        cachedUuid?.let {
            plugin.bannedPlayerList = getAddedBannedPlayerList(
                plugin.bannedPlayerList,
                it,
                name,
                calcCurrentTimeString(),
                sender.name,
                "forever",
                reason,
            )
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
