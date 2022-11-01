package com.github.tsuoihito.anyban.handlers

import com.github.tsuoihito.anyban.model.BannedPlayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.*

internal class BanHandlerKtTest {
    private val bannedPlayerList: List<BannedPlayer> = listOf<BannedPlayer>().plus(
        BannedPlayer(
            UUID.fromString("5eb4b606-5b7c-410c-afdd-47f919d3afa6"),
            "player1",
            "2022-01-01 00:01:00 +0900",
            "operator1",
            "forever",
            "player1 was banned"
        )
    ).plus(
        BannedPlayer(
            UUID.fromString("4936345f-6428-4ea7-a432-94c2d8224896"),
            "player2",
            "2022-01-01 00:02:00 +0900",
            "operator2",
            "forever",
            "player2 was banned"
        )
    ).plus(
        BannedPlayer(
            UUID.fromString("255cd800-d019-4d12-a6db-f6802741e829"),
            "player3",
            "2022-01-01 00:03:00 +0900",
            "operator3",
            "forever",
            "player3 was banned"
        )
    )

    @Test
    fun getNameBannedPlayer() {
        val result1 = getNameBannedPlayer(bannedPlayerList, "Player1")
        assertEquals("player1", result1?.name)
        assertEquals("2022-01-01 00:01:00 +0900", result1?.created)
        assertEquals("operator1", result1?.source)
        assertEquals("forever", result1?.expires)
        assertEquals("player1 was banned", result1?.reason)

        val result2 = getNameBannedPlayer(bannedPlayerList, "player4")
        assertNull(result2)
    }

    @Test
    fun getUuidBannedPlayer() {
        val result1 = getUuidBannedPlayer(
            bannedPlayerList,
            UUID.fromString("5eb4b606-5b7c-410c-afdd-47f919d3afa6")
        )
        assertEquals("player1", result1?.name)
        assertEquals("2022-01-01 00:01:00 +0900", result1?.created)
        assertEquals("operator1", result1?.source)
        assertEquals("forever", result1?.expires)
        assertEquals("player1 was banned", result1?.reason)

        val result2 = getUuidBannedPlayer(
            bannedPlayerList,
            UUID.fromString("c26d5d33-1e0b-46b3-aafd-2b87d0ea4661")
        )
        assertNull(result2)
    }

    @Test
    fun getAddedBannedPlayerList() {
        val result1 = getAddedBannedPlayerList(
            bannedPlayerList,
            UUID.fromString("07154076-a3db-421a-95bb-d73c0e06b6e1"),
            "player4",
            "2022-01-01 00:04:00 +0900",
            "operator4",
            "forever",
            "player4 was banned"
        ).find { it.name == "player4" }
        assertEquals("player4", result1?.name)
        assertEquals("2022-01-01 00:04:00 +0900", result1?.created)
        assertEquals("operator4", result1?.source)
        assertEquals("forever", result1?.expires)
        assertEquals("player4 was banned", result1?.reason)

        val result2 = getAddedBannedPlayerList(
            bannedPlayerList,
            UUID.fromString("07154076-a3db-421a-95bb-d73c0e06b6e1"),
            "player2",
            "2022-01-01 00:02:01 +0900",
            "operator2",
            "forever",
            "new reason"
        )
        assertEquals(listOf("player1", "player3", "player2"), result2.map { it.name })
        assertEquals("new reason", result2.find { it.name == "player2" }?.reason)
    }

    @Test
    fun getRemovedBannedPlayerList() {
        val result = getRemovedBannedPlayerList(bannedPlayerList, "player3")
            .find { it.name == "player3" }
        assertNull(result)
    }
}