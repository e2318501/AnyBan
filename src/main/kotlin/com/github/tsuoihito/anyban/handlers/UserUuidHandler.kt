package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.UserUuid
import java.util.*

fun getNewUserUuidList(
    userUuidList: List<UserUuid>,
    uuid: UUID,
    name: String,
    expiresOn: String,
    isExpired: (String) -> Boolean
): List<UserUuid> {
    return userUuidList.filterNot { it.name.equals(name, ignoreCase = true) }.plus(
        UserUuid(
            name,
            uuid,
            expiresOn,
        )
    ).filterNot { isExpired(it.expiresOn) }
}

fun getUuidFromCache(userUuidList: List<UserUuid>, name: String): UUID? {
    return userUuidList.find { it.name.equals(name, ignoreCase = true) }?.uuid
}

fun getPlayersCandidate(userUuidList: List<UserUuid>, typing: String): List<String> {
    return userUuidList.map { it.name }.filter { it.startsWith(typing) }
}
