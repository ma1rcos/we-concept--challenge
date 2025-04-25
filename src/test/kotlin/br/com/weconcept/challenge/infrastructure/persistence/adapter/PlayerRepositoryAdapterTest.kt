package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.persistence.repository.PlayerJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.Optional
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class PlayerRepositoryAdapterTest {

    private val playerJpaRepository = mockk<PlayerJpaRepository>()
    private val playerRepositoryAdapter = PlayerRepositoryAdapter(playerJpaRepository)

    @Test
    fun `should save player`() {
        val playerToSave = Player(name = "Test Player")
        val savedPlayer = playerToSave.copy(id = 1L, createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())
        every { playerJpaRepository.save(playerToSave) } returns savedPlayer
        val result = playerRepositoryAdapter.save(playerToSave)
        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
    }

    @Test
    fun `should find player by id`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerJpaRepository.findById(1L) } returns Optional.of(player)
        val result = playerRepositoryAdapter.findById(1L)
        assertEquals(1L, result?.id)
        assertEquals("Test Player", result?.name)
    }

    @Test
    fun `should return null when player not found by id`() {
        every { playerJpaRepository.findById(1L) } returns Optional.empty()
        val result = playerRepositoryAdapter.findById(1L)
        assertNull(result)
    }

    @Test
    fun `should find player by name`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerJpaRepository.findByName("Test Player") } returns player
        val result = playerRepositoryAdapter.findByName("Test Player")
        assertEquals(1L, result?.id)
        assertEquals("Test Player", result?.name)
    }

    @Test
    fun `should return null when player not found by name`() {
        every { playerJpaRepository.findByName("Test Player") } returns null
        val result = playerRepositoryAdapter.findByName("Test Player")
        assertNull(result)
    }

    @Test
    fun `should update player successfully`() {
        val existingPlayer = Player(
            id = 1L,
            name = "Old Name",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )
        val updatedPlayer = Player(
            id = 1L,
            name = "New Name"
        )
        val expectedSavedPlayer = existingPlayer.copy(
            name = "New Name",
            updatedAt = LocalDateTime.now()
        )
        every { playerJpaRepository.findById(1L) } returns Optional.of(existingPlayer)
        every { playerJpaRepository.save(any()) } returns expectedSavedPlayer
        val result = playerRepositoryAdapter.update(updatedPlayer)
        assertEquals(1L, result.id)
        assertEquals("New Name", result.name)
        assertNotEquals(existingPlayer.updatedAt, result.updatedAt)
        verify {
            playerJpaRepository.findById(1L)
            playerJpaRepository.save(any())
        }
    }

    @Test
    fun `should delete player by id`() {
        every { playerJpaRepository.deleteById(1L) } returns Unit
        playerRepositoryAdapter.deleteById(1L)
        verify { playerJpaRepository.deleteById(1L) }
    }
    
}