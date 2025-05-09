package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.RankingService
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.RankingMapper
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
class RankingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val rankingService = mockk<RankingService>()
    private val rankingMapper = mockk<RankingMapper>()
    private val controller = RankingController(rankingService, rankingMapper)

    @Test
    fun `getPlayerRanking should return player summary`() {
        val playerId = 1L
        val summary = PlayerRankingSummaryResponse(
            playerId = playerId,
            globalScore = 100,
            tournamentScores = mapOf(1L to 50)
        )

        every { rankingService.getPlayerRanking(playerId) } returns summary

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        val response = mockMvc.perform(get("/ranking/player/$playerId"))
            .andExpect(status().isOk)
            .andReturn()

        val jsonResponse = response.response.contentAsString
        val result = objectMapper.readValue(jsonResponse, PlayerRankingSummaryResponse::class.java)

        assertEquals(playerId, result.playerId)
        assertEquals(100, result.globalScore)
        assertEquals(50, result.tournamentScores[1L])
    }

    @TestConfiguration
    class Config {
        @Bean
        fun rankingService(): RankingService = mockk(relaxed = true)
        @Bean
        fun rankingMapper(): RankingMapper = mockk(relaxed = true)
    }

}