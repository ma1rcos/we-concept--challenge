package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.persistence.repository.PlayerJpaRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

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
    
}