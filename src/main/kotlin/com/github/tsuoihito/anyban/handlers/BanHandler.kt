package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.BannedPlayer
import java.util.*

fun getNameBannedPlayer(bannedPlayerList: List<BannedPlayer>, name: String): BannedPlayer? {
    return bannedPlayerList.find { it.name.equals(name, ignoreCase = true) }
}

fun getUuidBannedPlayer(bannedPlayerList: List<BannedPlayer>, uuid: UUID): BannedPlayer? {
    return bannedPlayerList.find { it.uuid == uuid }
}

fun getAddedBannedPlayerList(
    bannedPlayerList: List<BannedPlayer>,
    uuid: UUID,
    name: String,
    created: String,
    source: String,
    expires: String,
    reason: String
): List<BannedPlayer> {
    return bannedPlayerList.filterNot { it.name.equals(name, ignoreCase = true) }
        .plus(BannedPlayer(uuid, name, created, source, expires, reason))
}

fun getRemovedBannedPlayerList(bannedPlayerList: List<BannedPlayer>, name: String): List<BannedPlayer> {
    return bannedPlayerList.filterNot { it.name.equals(name, ignoreCase = true) }
}

fun getBannedPlayersCandidate(bannedPlayerList: List<BannedPlayer>, typing: String): List<String> {
    return bannedPlayerList.map { it.name }.filter { it.startsWith(typing) }
}
