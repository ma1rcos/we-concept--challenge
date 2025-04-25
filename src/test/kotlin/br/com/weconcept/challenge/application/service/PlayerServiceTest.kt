package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PlayerServiceTest {

    private val playerRepositoryPort = mockk<PlayerRepositoryPort>()
    private val playerService = PlayerService(playerRepositoryPort)

    @Test
    fun `should create player`() {
        val player = Player(name = "Test Player")
        val savedPlayer = player.copy(id = 1L)
        every { playerRepositoryPort.save(player) } returns savedPlayer
        val result = playerService.create(player)
        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
        verify { playerRepositoryPort.save(player) }
    }

    @Test
    fun `should find player by id`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        val result = playerService.getById(1L)
        assertEquals(1L, result?.id)
        assertEquals("Test Player", result?.name)
        verify { playerRepositoryPort.findById(1L) }
    }

    @Test
    fun `should return null when player not found by id`() {
        every { playerRepositoryPort.findById(1L) } returns null
        val result = playerService.getById(1L)
        assertNull(result)
        verify { playerRepositoryPort.findById(1L) }
    }

    @Test
    fun `should find player by name`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findByName("Test Player") } returns player
        val result = playerService.getByName("Test Player")
        assertEquals(1L, result?.id)
        assertEquals("Test Player", result?.name)
        verify { playerRepositoryPort.findByName("Test Player") }
    }

    @Test
    fun `should return null when player not found by name`() {
        every { playerRepositoryPort.findByName("Test Player") } returns null
        val result = playerService.getByName("Test Player")
        assertNull(result)
        verify { playerRepositoryPort.findByName("Test Player") }
    }

    @Test
    fun `should update player`() {
        val existingPlayer = Player(id = 1L, name = "Old Name")
        val updatedPlayer = existingPlayer.copy(name = "New Name", updatedAt = LocalDateTime.now())
        every { playerRepositoryPort.update(updatedPlayer) } returns updatedPlayer
        val result = playerService.update(updatedPlayer)
        assertEquals(1L, result.id)
        assertEquals("New Name", result.name)
    }

    @Test
    fun `should delete player by id`() {
        every { playerRepositoryPort.deleteById(1L) } returns Unit
        playerService.deleteById(1L)
        verify { playerRepositoryPort.deleteById(1L) }
    }

}