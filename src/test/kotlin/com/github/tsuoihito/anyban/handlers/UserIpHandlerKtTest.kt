package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.UserIp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserIpHandlerKtTest {
    private val userIpList: List<UserIp> = listOf<UserIp>().plus(
        UserIp("player1", listOf("192.168.0.1"))
    ).plus(
        UserIp("player2", listOf("192.168.0.2", "192.168.0.3"))
    )

    @Test
    fun getUserIp() {
        val result = getUserIp(userIpList, "player1")
        assertEquals("player1", result?.name)
        assertEquals(listOf("192.168.0.1"), result?.ips)
    }

    @Test
    fun getNewUserIpList() {
        val result1 = getNewUserIpList(userIpList, "player3", "192.168.1.1")
            .find { it.name == "player3" }
        assertEquals("player3", result1?.name)
        assertEquals(listOf("192.168.1.1"), result1?.ips)

        val result2 = getNewUserIpList(userIpList, "player1", "192.168.1.2")
            .find { it.name == "player1" }
        assertEquals("player1", result2?.name)
        assertEquals(listOf("192.168.0.1", "192.168.1.2"), result2?.ips)
    }
}