package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.BannedIp
import com.github.tsuoihito.anyban.model.UserIp

fun getBannedIp(bannedIpList: List<BannedIp>, userIpList: List<UserIp>, name: String): BannedIp? {
    return userIpList.find { it.name.equals(name, ignoreCase = true) }
        ?.let { userIp -> bannedIpList.find { userIp.ips.contains(it.ip) } }
}

fun getAddedBannedIpList(
    bannedIpList: List<BannedIp>,
    ip: String,
    created: String,
    source: String,
    expires: String,
    reason: String,
): List<BannedIp> {
    return bannedIpList.filterNot { it.ip == ip }.plus(BannedIp(ip, created, source, expires, reason))
}

fun getRemovedBannedIpList(bannedIpList: List<BannedIp>, userIpList: List<UserIp>, name: String): List<BannedIp> {
    return bannedIpList.filterNot { bannedIp ->
        userIpList.find { userIp ->
            userIp.name.equals(
                name,
                ignoreCase = true
            )
        }.let { it != null && it.ips.contains(bannedIp.ip) }
    }
}
