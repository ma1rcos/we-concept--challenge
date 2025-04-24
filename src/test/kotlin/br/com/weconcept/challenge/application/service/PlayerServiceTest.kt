package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlayerServiceTest {

    private val playerRepositoryPort = mockk<PlayerRepositoryPort>()
    private val playerService = PlayerService(playerRepositoryPort)

    @Test
    fun `should create player`() {
        val player = Player(name = "Test Player")
        val savedPlayer = player.copy(id = 1L)
        every { playerRepositoryPort.save(player) } returns savedPlayer
        val result = playerService.createPlayer(player)
        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
    }

}