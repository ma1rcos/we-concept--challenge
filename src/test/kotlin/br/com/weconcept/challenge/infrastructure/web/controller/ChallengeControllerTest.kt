package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.ChallengeService
import br.com.weconcept.challenge.domain.model.ChallengeResult
import br.com.weconcept.challenge.infrastructure.web.dto.request.FibonacciChallengeRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.ChallengeResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.ChallengeMapper
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
@AutoConfigureMockMvc
class ChallengeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val challengeService = mockk<ChallengeService>()
    private val challengeMapper = mockk<ChallengeMapper>()
    private val controller = ChallengeController(challengeService, challengeMapper)

    @Test
    fun `executeFibonacci should return challenge result`() {
        val request = FibonacciChallengeRequest(playerId = 1L, number = 10)
        val result = ChallengeResult(
            challengeName = "Fibonacci",
            success = true,
            score = 10,
            result = 55,
            executionId = 1L
        )

        every { challengeService.executeFibonacciChallenge(1L, 10, null) } returns result
        every { challengeMapper.toResponse(result) } returns result.toResponse()

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        mockMvc.perform(
            post("/challenge/fibonacci")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.challengeName").value("Fibonacci"))
            .andExpect(jsonPath("$.result").value(55))
    }
}

private fun ChallengeResult.toResponse() = ChallengeResponse(
    challengeName = challengeName,
    success = success,
    score = score,
    result = result,
    executionId = executionId
)