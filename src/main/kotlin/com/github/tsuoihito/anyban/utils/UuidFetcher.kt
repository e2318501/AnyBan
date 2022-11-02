package com.github.tsuoihito.anyban.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.geysermc.floodgate.api.FloodgateApi
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutionException

fun fetchUuid(mapper: ObjectMapper, name: String, floodgateEnabled: Boolean): UUID? {
    return if (!floodgateEnabled) {
        getUUIDFromMojang(mapper, name)
    } else if (isFloodgatePlayer(name)) {
        getUUIDFromFloodgate(name)
    } else {
        getUUIDFromMojang(mapper, name)
    }
}

private fun getUUIDFromMojang(mapper: ObjectMapper, name: String): UUID? {
    return try {
        val url = URL("https://api.mojang.com/users/profiles/minecraft/${name}")
        val uuidAsString = mapper.readTree(url).get("id")?.toString()?.replace("\"", "")
        if (uuidAsString == "null") UUID.fromString("0-0-0-0-0") else UUID.fromString(
            StringBuffer(uuidAsString)
                .insert(8, "-")
                .insert(13, "-")
                .insert(18, "-")
                .insert(23, "-")
                .toString()
        )
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun getUUIDFromFloodgate(name: String): UUID? {
    try {
        return FloodgateApi.getInstance().getUuidFor(name.substring(FloodgateApi.getInstance().playerPrefix.length))
            .get()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: ExecutionException) {
        e.printStackTrace()
    }
    return null
}

private fun isFloodgatePlayer(name: String): Boolean {
    return name.startsWith(FloodgateApi.getInstance().playerPrefix)
}
