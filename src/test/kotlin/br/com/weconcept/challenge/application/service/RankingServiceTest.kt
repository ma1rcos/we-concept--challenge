package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.RankingRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Ranking
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RankingServiceTest {

    private val rankingRepositoryPort = mockk<RankingRepositoryPort>()
    private val playerService = mockk<PlayerService>()
    private val rankingService = RankingService(rankingRepositoryPort, playerService)

    @Test
    fun `updatePlayerScore should update global and tournament rankings`() {
        val playerId = 1L
        val tournamentId = 1L
        every { playerService.getById(playerId) } returns Player(id = playerId, name = "Test")
        every { rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId) } returns null
        every { rankingRepositoryPort.findByPlayerAndTournament(playerId, tournamentId) } returns null
        every { rankingRepositoryPort.save(any()) } returnsArgument 0

        rankingService.updatePlayerScore(playerId, 10, tournamentId)

        verify {
            rankingRepositoryPort.save(withArg {
                assertEquals(playerId, it.playerId)
                assertEquals(10, it.totalScore)
                assertNull(it.tournamentId)
            })
            rankingRepositoryPort.save(withArg {
                assertEquals(playerId, it.playerId)
                assertEquals(10, it.totalScore)
                assertEquals(tournamentId, it.tournamentId)
            })
        }
    }

    @Test
    fun `updatePlayerScore should throw BadRequestException for negative score`() {
        val playerId = 1L
        every { playerService.getById(playerId) } returns Player(id = playerId, name = "Test")

        val exception = assertThrows<BadRequestException> {
            rankingService.updatePlayerScore(playerId, -1, null)
        }
        assertEquals(Message.INVALID_SCORE_VALUE, exception.message)
    }

    @Test
    fun `getGlobalRankings should return rankings ordered by score`() {
        val rankings = listOf(
            Ranking(playerId = 1L, totalScore = 100),
            Ranking(playerId = 2L, totalScore = 50)
        )
        every { rankingRepositoryPort.findAllByTournamentIsNullOrderByTotalScoreDesc() } returns rankings

        val result = rankingService.getGlobalRankings()
        assertEquals(2, result.size)
        assertEquals(100, result[0].totalScore)
        assertEquals(50, result[1].totalScore)
    }

    @Test
    fun `getTournamentRankings should return tournament rankings`() {
        val tournamentId = 1L
        val rankings = listOf(
            Ranking(playerId = 1L, tournamentId = tournamentId, totalScore = 100),
            Ranking(playerId = 2L, tournamentId = tournamentId, totalScore = 50)
        )
        every { rankingRepositoryPort.findAllByTournamentOrderByTotalScoreDesc(tournamentId) } returns rankings

        val result = rankingService.getTournamentRankings(tournamentId)
        assertEquals(2, result.size)
        assertEquals(tournamentId, result[0].tournamentId)
    }

    @Test
    fun `getGlobalRankingForPlayer should return ranking`() {
        val playerId = 1L
        val ranking = Ranking(playerId = playerId, totalScore = 100)
        every { playerService.getById(playerId) } returns Player(id = playerId, name = "Test")
        every { rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId) } returns ranking

        val result = rankingService.getGlobalRankingForPlayer(playerId)
        assertEquals(100, result?.totalScore)
    }

    @Test
    fun `getTournamentRankingsForPlayer should return player tournament rankings`() {
        val playerId = 1L
        val rankings = listOf(
            Ranking(playerId = playerId, tournamentId = 1L, totalScore = 100),
            Ranking(playerId = playerId, tournamentId = 2L, totalScore = 50)
        )
        every { playerService.getById(playerId) } returns Player(id = playerId, name = "Test")
        every { rankingRepositoryPort.findAllByPlayer(playerId) } returns rankings

        val result = rankingService.getTournamentRankingsForPlayer(playerId)
        assertEquals(2, result.size)
        assertEquals(1L, result[0].tournamentId)
    }

    @Test
    fun `getPlayerRanking should return summary with scores`() {
        val playerId = 1L
        val globalRanking = Ranking(playerId = playerId, totalScore = 100)
        val tournamentRanking = Ranking(playerId = playerId, tournamentId = 1L, totalScore = 50)

        every { playerService.getById(playerId) } returns Player(id = playerId, name = "Test")
        every { rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId) } returns globalRanking
        every { rankingRepositoryPort.findAllByPlayer(playerId) } returns listOf(tournamentRanking)

        val result = rankingService.getPlayerRanking(playerId)
        assertEquals(playerId, result.playerId)
        assertEquals(100, result.globalScore)
        assertEquals(50, result.tournamentScores[1L])
    }

}