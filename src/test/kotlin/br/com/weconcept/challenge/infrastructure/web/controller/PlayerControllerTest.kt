package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var playerService: PlayerService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create player`() {
        val request = CreatePlayerRequest(name = "Test Player")
        every { playerService.create(any()) } returns Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        mockMvc.perform(
            post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Player"))
    }

    @Test
    fun `should find player by id`() {
        every { playerService.getById(1L) } returns Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        mockMvc.perform(get("/player/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Player"))
    }

    @Test
    fun `should return not found when player does not exist by id`() {
        every { playerService.getById(1L) } returns null

        mockMvc.perform(get("/player/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should find player by name`() {
        every { playerService.getByName("Test Player") } returns Player(
            id = 1L,
            name = "Test Player",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        mockMvc.perform(get("/player").param("name", "Test Player"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Player"))
    }

    @Test
    fun `should return not found when player does not exist by name`() {
        every { playerService.getByName("Test Player") } returns null

        mockMvc.perform(get("/player").param("name", "Test Player"))
            .andExpect(status().isNotFound)
    }

    @TestConfiguration
    class Config {
        @Bean
        fun playerService(): PlayerService = mockk(relaxed = true)
    }

}