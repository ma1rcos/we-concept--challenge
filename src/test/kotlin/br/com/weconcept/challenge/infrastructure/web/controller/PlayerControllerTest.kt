package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.request.UpdatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

class PlayerControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper
    private val playerService: PlayerService = mockk()
    private val playerMapper: PlayerMapper = mockk()
    private val controller = PlayerController(playerService, playerMapper)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper = ObjectMapper()
    }

    @Test
    fun `should create player`() {
        val request = CreatePlayerRequest(name = "Test Player")
        val player = Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val expectedResponse = PlayerResponse(
            id = 1L,
            name = "Test Player",
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )

        every { playerMapper.toDomain(request) } returns player
        every { playerService.create(player) } returns player
        every { playerMapper.toResponse(player) } returns expectedResponse

        mockMvc.perform(
            post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(expectedResponse.id))
            .andExpect(jsonPath("$.name").value(expectedResponse.name))
    }

    @Test
    fun `should find player by id`() {
        val player = Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val expectedResponse = PlayerResponse(
            id = 1L,
            name = "Test Player",
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )

        every { playerService.getById(1L) } returns player
        every { playerMapper.toResponse(player) } returns expectedResponse

        mockMvc.perform(get("/player/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(expectedResponse.id))
            .andExpect(jsonPath("$.name").value(expectedResponse.name))
    }

    @Test
    fun `should return not found when player does not exist by id`() {
        every { playerService.getById(1L) } throws NotFoundException(Message.PLAYER_NOT_FOUND_ID.format(1L))

        mockMvc.perform(get("/player/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should find player by name`() {
        val player = Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val expectedResponse = PlayerResponse(
            id = 1L,
            name = "Test Player",
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )

        every { playerService.getByName("Test Player") } returns player
        every { playerMapper.toResponse(player) } returns expectedResponse

        mockMvc.perform(get("/player").param("name", "Test Player"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(expectedResponse.id))
            .andExpect(jsonPath("$.name").value(expectedResponse.name))
    }

    @Test
    fun `should return not found when player does not exist by name`() {
        every { playerService.getByName("Test Player") } throws NotFoundException(Message.PLAYER_NOT_FOUND_NAME.format("Test Player"))

        mockMvc.perform(get("/player").param("name", "Test Player"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should update player`() {
        val request = UpdatePlayerRequest(name = "Updated Name")
        val existingPlayer = Player(id = 1L, name = "Old Name")
        val updatedPlayer = Player(
            id = 1L,
            name = "Updated Name",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val expectedResponse = PlayerResponse(
            id = 1L,
            name = "Updated Name",
            createdAt = updatedPlayer.createdAt,
            updatedAt = updatedPlayer.updatedAt
        )

        every { playerService.getById(1L) } returns existingPlayer
        every { playerService.update(any()) } returns updatedPlayer
        every { playerMapper.toResponse(updatedPlayer) } returns expectedResponse

        mockMvc.perform(
            put("/player/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(expectedResponse.id))
            .andExpect(jsonPath("$.name").value(expectedResponse.name))
    }

    @Test
    fun `should delete player`() {
        every { playerService.deleteById(1L) } returns Unit

        mockMvc.perform(delete("/player/1"))
            .andExpect(status().isNoContent)
    }

}