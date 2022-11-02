package com.github.tsuoihito.anyban.listeners

import com.github.tsuoihito.anyban.AnyBan
import com.github.tsuoihito.anyban.handlers.getBannedIp
import com.github.tsuoihito.anyban.handlers.getNewUserIpList
import com.github.tsuoihito.anyban.handlers.getNewUserUuidList
import com.github.tsuoihito.anyban.handlers.getUuidBannedPlayer
import com.github.tsuoihito.anyban.utils.calcAMonthLaterString
import com.github.tsuoihito.anyban.utils.calcIsExpired
import com.github.tsuoihito.anyban.utils.getMessage
import net.md_5.bungee.api.event.LoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.net.InetSocketAddress
import java.util.concurrent.CompletableFuture

class LoginListener(private val plugin: AnyBan) : Listener {
    @EventHandler
    fun onLogin(event: LoginEvent) {
        val name = event.connection.name
        val uuid = event.connection.uniqueId
        val ip = (event.connection.socketAddress as InetSocketAddress).address.hostAddress
        getUuidBannedPlayer(plugin.bannedPlayerList, uuid)?.let {
            event.setCancelReason(
                getMessage(plugin.config, "message.youAreBanned", false) { s -> s.replace("%reason%", it.reason) }
            )
            event.isCancelled = true
        }
        getBannedIp(plugin.bannedIpList, plugin.userIpList, name)?.let {
            event.setCancelReason(
                getMessage(plugin.config, "message.youAreBanned", false) { s -> s.replace("%reason%", it.reason) }
            )
            event.isCancelled = true
        }
        plugin.userUuidList =
            getNewUserUuidList(plugin.userUuidList, uuid, name, calcAMonthLaterString()) { calcIsExpired(it) }
        plugin.userIpList = getNewUserIpList(plugin.userIpList, name, ip)

        CompletableFuture.runAsync { plugin.saveData() }
    }
}
