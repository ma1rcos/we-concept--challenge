package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class PlayerMapperTest {

    private val playerMapper = PlayerMapper()

    @Test
    fun `toDomain should map CreatePlayerRequest to Player with correct name`() {
        val request = CreatePlayerRequest(name = "Test Player")
        val result = playerMapper.toDomain(request)
        assertEquals("Test Player", result.name)
    }

    @Test
    fun `toResponse should map Player to PlayerResponse preserving all fields`() {
        val now = LocalDateTime.now()
        val player = Player(
            id = 1L,
            name = "Test Player",
            createdAt = now,
            updatedAt = now
        )
        val result = playerMapper.toResponse(player)
        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
        assertEquals(now, result.createdAt)
        assertEquals(now, result.updatedAt)
    }

}