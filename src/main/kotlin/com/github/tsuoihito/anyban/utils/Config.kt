package com.github.tsuoihito.anyban.utils

import com.github.tsuoihito.anyban.AnyBan
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.IOException
import java.nio.file.Files

fun loadConfig(plugin: AnyBan): Configuration? {
    val configFile = File(plugin.dataFolder, "config.yml")
    return try {
        plugin.dataFolder.mkdir()
        if (!configFile.exists()) {
            Files.copy(plugin.getResourceAsStream("config.yml"), configFile.toPath())
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(configFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
