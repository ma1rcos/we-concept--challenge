package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Ranking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RankingMapperTest {

    private val mapper = RankingMapper()

    @Test
    fun `toResponse should map Ranking to RankingResponse`() {
        val ranking = Ranking(
            playerId = 1L,
            totalScore = 100,
            tournamentId = 1L
        )

        val response = mapper.toResponse(ranking)

        assertEquals(1L, response.playerId)
        assertEquals(100, response.totalScore)
        assertEquals(1L, response.tournamentId)
    }

    @Test
    fun `toResponse should handle null tournamentId`() {
        val ranking = Ranking(
            playerId = 1L,
            totalScore = 100,
            tournamentId = null
        )

        val response = mapper.toResponse(ranking)

        assertEquals(1L, response.playerId)
        assertEquals(100, response.totalScore)
        assertNull(response.tournamentId)
    }

}