package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.RankingRepositoryPort
import br.com.weconcept.challenge.domain.model.Ranking
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RankingServiceTest {

    private val rankingRepositoryPort = mockk<RankingRepositoryPort>()
    private val rankingService = RankingService(rankingRepositoryPort)

    @Test
    fun `updatePlayerScore should update global and tournament rankings`() {
        val playerId = 1L
        val tournamentId = 1L
        every { rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId) } returns null
        every { rankingRepositoryPort.findByPlayerAndTournament(playerId, tournamentId) } returns null
        every { rankingRepositoryPort.save(any()) } returnsArgument 0
        rankingService.updatePlayerScore(playerId, 10, tournamentId)
        verify {
            rankingRepositoryPort.save(withArg {
                assertEquals(playerId, it.playerId)
                assertEquals(10, it.totalScore)
                assertEquals(tournamentId, it.tournamentId)
            })
        }
    }

    @Test
    fun `getPlayerRanking should return summary with scores`() {
        val playerId = 1L
        val globalRanking = Ranking(playerId = playerId, totalScore = 100)
        val tournamentRanking = Ranking(playerId = playerId, tournamentId = 1L, totalScore = 50)
        every { rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId) } returns globalRanking
        every { rankingRepositoryPort.findAllByPlayer(playerId) } returns listOf(tournamentRanking)
        val result = rankingService.getPlayerRanking(playerId)
        assertEquals(100, result.globalScore)
        assertEquals(1, result.tournamentScores.size)
        assertEquals(50, result.tournamentScores[1L])
    }

}