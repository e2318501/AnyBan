package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.UserUuid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class UserUuidHandlerKtTest {
    private val userUuidList: List<UserUuid> = listOf<UserUuid>().plus(
        UserUuid(
            "player1",
            UUID.fromString("5eb4b606-5b7c-410c-afdd-47f919d3afa6"),
            "forever"
        )
    ).plus(
        UserUuid(
            "player2",
            UUID.fromString("4936345f-6428-4ea7-a432-94c2d8224896"),
            "forever"
        )
    )

    @Test
    fun getNewUserUuidList() {
        val result = getNewUserUuidList(
            userUuidList,
            UUID.fromString("255cd800-d019-4d12-a6db-f6802741e829"),
            "player3",
            "forever"
        ) { false }.find { it.name == "player3" }
        assertEquals("player3", result?.name)
        assertEquals(UUID.fromString("255cd800-d019-4d12-a6db-f6802741e829"), result?.uuid)
        assertEquals("forever", result?.expiresOn)
    }

    @Test
    fun getUuidFromCache() {
        val result = getUuidFromCache(userUuidList, "player2")
        assertEquals(UUID.fromString("4936345f-6428-4ea7-a432-94c2d8224896"), result)
    }
}