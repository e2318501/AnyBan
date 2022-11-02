package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.BannedIp
import com.github.tsuoihito.anyban.model.UserIp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class IpBanHandlerKtTest {
    private val bannedIpList: List<BannedIp> = listOf<BannedIp>().plus(
        BannedIp(
            "192.168.0.1",
            "2022-01-01 00:01:00 +0900",
            "operator1",
            "forever",
            "player1 was banned",
        )
    ).plus(
        BannedIp(
            "192.168.0.2",
            "2022-01-01 00:02:00 +0900",
            "operator2",
            "forever",
            "player2 was banned",
        )
    ).plus(
        BannedIp(
            "192.168.0.3",
            "2022-01-01 00:02:00 +0900",
            "operator2",
            "forever",
            "player2 was banned",
        )
    )
    private val userIpList: List<UserIp> = listOf<UserIp>().plus(
        UserIp("player1", listOf("192.168.0.1"))
    ).plus(
        UserIp("player2", listOf("192.168.0.2", "192.168.0.3"))
    )

    @Test
    fun getBannedIp() {
        val result = getBannedIp(bannedIpList, userIpList, "player1")
        assertEquals("192.168.0.1", result?.ip)
        assertEquals("2022-01-01 00:01:00 +0900", result?.created)
        assertEquals("operator1", result?.source)
        assertEquals("forever", result?.expires)
        assertEquals("player1 was banned", result?.reason)
    }

    @Test
    fun getAddedBannedIpList() {
        val result = getAddedBannedIpList(
            bannedIpList,
            "192.168.1.1",
            "2022-01-01 00:03:00 +0900",
            "operator3",
            "forever",
            "192.168.1.1 was banned"
        ).find { it.ip == "192.168.1.1" }
        assertEquals("192.168.1.1", result?.ip)
        assertEquals("2022-01-01 00:03:00 +0900", result?.created)
        assertEquals("operator3", result?.source)
        assertEquals("forever", result?.expires)
        assertEquals("192.168.1.1 was banned", result?.reason)
    }

    @Test
    fun getRemovedBannedIpList() {
        val result1 = getRemovedBannedIpList(bannedIpList, userIpList, "player2")
            .find { it.ip == "192.168.0.2" }
        val result2 = getRemovedBannedIpList(bannedIpList, userIpList, "player2")
            .find { it.ip == "192.168.0.3" }
        assertNull(result1)
        assertNull(result2)
    }
}