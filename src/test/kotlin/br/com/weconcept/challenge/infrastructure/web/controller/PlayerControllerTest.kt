package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.beans.factory.annotation.Autowired

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
        every { playerService.createPlayer(any()) } returns Player(
            id = 1L,
            name = "Test Player"
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

    @TestConfiguration
    class Config {
        @Bean
        fun playerService(): PlayerService = mockk()
    }

}