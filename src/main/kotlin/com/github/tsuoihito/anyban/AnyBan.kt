package com.github.tsuoihito.anyban

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tsuoihito.anyban.commands.*
import com.github.tsuoihito.anyban.listeners.LoginListener
import com.github.tsuoihito.anyban.model.BannedIp
import com.github.tsuoihito.anyban.model.BannedPlayer
import com.github.tsuoihito.anyban.model.UserIp
import com.github.tsuoihito.anyban.model.UserUuid
import com.github.tsuoihito.anyban.utils.loadConfig
import com.github.tsuoihito.anyban.utils.loadList
import com.github.tsuoihito.anyban.utils.saveList
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration

const val bannedPlayerListFileName = "banned-players.json"
const val userUuidListFileName = "usercache.json"
const val bannedIpListFileName = "banned-ips.json"
const val userIpListFileName = "ipcache.json"

class AnyBan : Plugin() {
    var config: Configuration? = null
    var bannedPlayerList: List<BannedPlayer> = listOf()
    var userUuidList: List<UserUuid> = listOf()
    var bannedIpList: List<BannedIp> = listOf()
    var userIpList: List<UserIp> = listOf()
    val mapper = jacksonObjectMapper()

    override fun onEnable() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        config = loadConfig(this)
        loadData()
        register()
    }

    override fun onDisable() {
        saveData()
    }

    fun loadData() {
        bannedPlayerList = loadList(BannedPlayer::class.java, mapper, dataFolder, bannedPlayerListFileName)
        userUuidList = loadList(UserUuid::class.java, mapper, dataFolder, userUuidListFileName)
        bannedIpList = loadList(BannedIp::class.java, mapper, dataFolder, bannedIpListFileName)
        userIpList = loadList(UserIp::class.java, mapper, dataFolder, userIpListFileName)
    }

    fun saveData() {
        saveList(mapper, dataFolder, bannedPlayerListFileName, bannedPlayerList)
        saveList(mapper, dataFolder, userUuidListFileName, userUuidList)
        saveList(mapper, dataFolder, bannedIpListFileName, bannedIpList)
        saveList(mapper, dataFolder, userIpListFileName, userIpList)
    }

    private fun register() {
        val pm = ProxyServer.getInstance().pluginManager
        pm.registerCommand(this, AdminCommand(this))
        pm.registerCommand(this, BanCommand(this))
        pm.registerCommand(this, BanIpCommand(this))
        pm.registerCommand(this, UnBanCommand(this))
        pm.registerCommand(this, UnBanIpCommand(this))
        pm.registerCommand(this, IsBanCommand(this))
        pm.registerCommand(this, IsBanIpCommand(this))
        pm.registerListener(this, LoginListener(this))
    }
}
