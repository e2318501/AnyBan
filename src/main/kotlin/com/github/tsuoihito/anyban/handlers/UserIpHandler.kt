package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.UserIp

fun getUserIp(userIpList: List<UserIp>, name: String): UserIp? {
    return userIpList.find { it.name.equals(name, ignoreCase = true) }
}

fun getNewUserIpList(userIpList: List<UserIp>, name: String, ip: String): List<UserIp> {
    return userIpList.filterNot { it.name.equals(name, ignoreCase = true) }.plus(
        userIpList.find { it.name.equals(name, ignoreCase = true) }?.let { oldUserIp ->
            UserIp(name, oldUserIp.ips.plus(ip))
        } ?: UserIp(name, listOf(ip))
    )
}
