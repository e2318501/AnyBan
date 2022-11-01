package com.github.tsuoihito.anyban.utils

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.TextComponent

fun kickPlayer(proxyServer: ProxyServer, name: String, reason: TextComponent) {
    val player = proxyServer.getPlayer(name)
    player?.disconnect(reason)
}
