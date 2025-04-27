package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.TournamentService
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
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
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class TournamentControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var tournamentService: TournamentService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create tournament successfully`() {
        val request = CreateTournamentRequest(
            name = "Test Tournament",
            date = LocalDate.now()
        )
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now()
        )
        every { tournamentService.create(any()) } returns tournament
        mockMvc.perform(
            post("/tournament")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Tournament"))
    }

    @Test
    fun `should add player to tournament successfully`() {
        val player = Player(id = 1L, name = "Test Player")
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now(),
            players = mutableSetOf(player)
        )
        every { tournamentService.addPlayer(1L, 1L) } returns tournament   
        mockMvc.perform(
            post("/tournament/1/player/1")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.players.length()").value(1))
            .andExpect(jsonPath("$.players[0].id").value(1))
    }

    @Test
    fun `should remove player from tournament successfully`() {
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now(),
            players = mutableSetOf()
        )
        every { tournamentService.removePlayer(1L, 1L) } returns tournament
        mockMvc.perform(
            delete("/tournament/1/player/1")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.players.length()").value(0))
    }

    @TestConfiguration
    class Config {
        @Bean
        fun tournamentService(): TournamentService = mockk(relaxed = true)
    }
}