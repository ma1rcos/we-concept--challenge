package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.ConflictException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.Player
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PlayerServiceTest {

    private val playerRepositoryPort = mockk<PlayerRepositoryPort>()
    private val playerService = PlayerService(playerRepositoryPort)

    @Test
    fun `create should return saved player when valid`() {
        val player = Player(name = "Test Player")
        val savedPlayer = player.copy(id = 1L)

        every { playerRepositoryPort.save(player) } returns savedPlayer
        every { playerRepositoryPort.findByName(any()) } returns null

        val result = playerService.create(player)

        assertEquals(1L, result.id)
        assertEquals("Test Player", result.name)
        verify { playerRepositoryPort.save(player) }
    }

    @Test
    fun `create should throw BadRequestException when name is blank`() {
        val player = Player(name = " ")

        val exception = assertThrows<BadRequestException> {
            playerService.create(player)
        }

        assertEquals(Message.PLAYER_NAME_EMPTY, exception.message)
    }

    @Test
    fun `create should throw ConflictException when name already exists`() {
        val player = Player(name = "Existing Player")
        val existingPlayer = player.copy(id = 2L)

        every { playerRepositoryPort.findByName("Existing Player") } returns existingPlayer

        val exception = assertThrows<ConflictException> {
            playerService.create(player)
        }

        assertEquals(Message.PLAYER_NAME_EXISTS.format("Existing Player"), exception.message)
    }

    @Test
    fun `update should return updated player when valid`() {
        val existingPlayer = Player(id = 1L, name = "Old Name")
        val updatedPlayer = existingPlayer.copy(name = "New Name")

        every { playerRepositoryPort.existsById(1L) } returns true
        every { playerRepositoryPort.findByName("New Name") } returns null
        every { playerRepositoryPort.update(updatedPlayer) } returns updatedPlayer

        val result = playerService.update(updatedPlayer)

        assertEquals("New Name", result.name)
        verify { playerRepositoryPort.update(updatedPlayer) }
    }

    @Test
    fun `update should throw NotFoundException when player not found`() {
        val player = Player(id = 1L, name = "Test")

        every { playerRepositoryPort.existsById(1L) } returns false

        val exception = assertThrows<NotFoundException> {
            playerService.update(player)
        }

        assertEquals(Message.PLAYER_NOT_FOUND_ID.format(1L), exception.message)
    }

    @Test
    fun `update should throw ConflictException when name already exists for another player`() {
        val player = Player(id = 1L, name = "New Name")
        val existingPlayer = Player(id = 2L, name = "New Name")

        every { playerRepositoryPort.existsById(1L) } returns true
        every { playerRepositoryPort.findByName("New Name") } returns existingPlayer

        val exception = assertThrows<ConflictException> {
            playerService.update(player)
        }

        assertEquals(Message.PLAYER_NAME_EXISTS.format("New Name"), exception.message)
    }

    @Test
    fun `getById should return player when found`() {
        val player = Player(id = 1L, name = "Test")

        every { playerRepositoryPort.findById(1L) } returns player

        val result = playerService.getById(1L)

        assertEquals(player, result)
    }

    @Test
    fun `getById should throw NotFoundException when player not found`() {
        every { playerRepositoryPort.findById(1L) } returns null

        val exception = assertThrows<NotFoundException> {
            playerService.getById(1L)
        }

        assertEquals(Message.PLAYER_NOT_FOUND_ID.format(1L), exception.message)
    }

    @Test
    fun `getByName should return player when found`() {
        val player = Player(id = 1L, name = "Test")

        every { playerRepositoryPort.findByName("Test") } returns player

        val result = playerService.getByName("Test")

        assertEquals(player, result)
    }

    @Test
    fun `getByName should throw NotFoundException when player not found`() {
        every { playerRepositoryPort.findByName("Test") } returns null

        val exception = assertThrows<NotFoundException> {
            playerService.getByName("Test")
        }

        assertEquals(Message.PLAYER_NOT_FOUND_NAME.format("Test"), exception.message)
    }

    @Test
    fun `deleteById should delete when player exists`() {
        every { playerRepositoryPort.existsById(1L) } returns true
        every { playerRepositoryPort.deleteById(1L) } returns Unit

        playerService.deleteById(1L)

        verify { playerRepositoryPort.deleteById(1L) }
    }

    @Test
    fun `deleteById should throw NotFoundException when player not found`() {
        every { playerRepositoryPort.existsById(1L) } returns false

        val exception = assertThrows<NotFoundException> {
            playerService.deleteById(1L)
        }

        assertEquals(Message.PLAYER_NOT_FOUND_ID.format(1L), exception.message)
    }

}