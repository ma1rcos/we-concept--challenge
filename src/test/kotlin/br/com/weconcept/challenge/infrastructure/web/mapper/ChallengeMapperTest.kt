package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.ChallengeResult
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChallengeMapperTest {

    private val mapper = ChallengeMapper()

    @Test
    fun `toResponse should map ChallengeResult to ChallengeResponse`() {
        val result = ChallengeResult(
            challengeName = "Fibonacci",
            success = true,
            score = 10,
            result = 55,
            executionId = 1L
        )

        val response = mapper.toResponse(result)

        assertEquals("Fibonacci", response.challengeName)
        assertEquals(true, response.success)
        assertEquals(10, response.score)
        assertEquals(55, response.result)
        assertEquals(1L, response.executionId)
    }

}