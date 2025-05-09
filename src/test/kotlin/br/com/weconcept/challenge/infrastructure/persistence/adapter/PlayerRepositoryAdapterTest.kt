package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.persistence.repository.PlayerJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Optional

class PlayerRepositoryAdapterTest {

    private val playerJpaRepository = mockk<PlayerJpaRepository>()
    private val adapter = PlayerRepositoryAdapter(playerJpaRepository)

    @Test
    fun `should save player`() {
        val player = Player(name = "Test Player")
        val savedPlayer = player.copy(id = 1L)

        every { playerJpaRepository.save(player) } returns savedPlayer

        val result = adapter.save(player)

        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
        verify { playerJpaRepository.save(player) }
    }

    @Test
    fun `should update player successfully`() {
        val player = Player(id = 1L, name = "Test Player")

        every { playerJpaRepository.save(player) } returns player

        val result = adapter.update(player)

        assertEquals(player.id, result.id)
        assertEquals(player.name, result.name)
        verify { playerJpaRepository.save(player) }
    }

    @Test
    fun `should find player by id`() {
        val player = Player(id = 1L, name = "Test Player")

        every { playerJpaRepository.findById(1L) } returns Optional.of(player)

        val result = adapter.findById(1L)

        assertEquals(player, result)
    }

    @Test
    fun `should return null when player not found`() {
        every { playerJpaRepository.findById(1L) } returns Optional.empty()

        val result = adapter.findById(1L)

        assertNull(result)
    }

    @Test
    fun `should find player by name`() {
        val player = Player(id = 1L, name = "Test Player")

        every { playerJpaRepository.findByName("Test Player") } returns player

        val result = adapter.findByName("Test Player")

        assertEquals(player, result)
    }

    @Test
    fun `should check if player exists by id`() {
        every { playerJpaRepository.existsById(1L) } returns true

        val result = adapter.existsById(1L)

        assertTrue(result)
    }

    @Test
    fun `should delete player by id`() {
        every { playerJpaRepository.deleteById(1L) } returns Unit

        adapter.deleteById(1L)

        verify { playerJpaRepository.deleteById(1L) }
    }

}