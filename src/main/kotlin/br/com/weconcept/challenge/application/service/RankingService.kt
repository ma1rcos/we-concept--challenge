package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.RankingRepositoryPort
import br.com.weconcept.challenge.domain.model.Ranking
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RankingService(
    private val rankingRepositoryPort: RankingRepositoryPort
) {

    fun getGlobalRankings(): List<Ranking> = rankingRepositoryPort.findAllByTournamentIsNullOrderByTotalScoreDesc()
    fun getTournamentRankings(tournamentId: Long): List<Ranking> = rankingRepositoryPort.findAllByTournamentOrderByTotalScoreDesc(tournamentId)
    fun getGlobalRankingForPlayer(playerId: Long): Ranking? = rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId)
    fun getTournamentRankingsForPlayer(playerId: Long): List<Ranking> = rankingRepositoryPort.findAllByPlayer(playerId).filter { it.tournamentId != null }

    @Transactional
    fun updatePlayerScore(playerId: Long, scoreToAdd: Int, tournamentId: Long? = null) {
        updateGlobalRanking(playerId, scoreToAdd)
        tournamentId?.let { updateTournamentRanking(playerId, scoreToAdd, it) }
    }

    private fun updateGlobalRanking(playerId: Long, scoreToAdd: Int) {
        val globalRanking = rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId)
            ?: Ranking(playerId = playerId, totalScore = 0)
        globalRanking.totalScore += scoreToAdd
        rankingRepositoryPort.save(globalRanking)
    }

    private fun updateTournamentRanking(playerId: Long, scoreToAdd: Int, tournamentId: Long) {
        val tournamentRanking = rankingRepositoryPort.findByPlayerAndTournament(playerId, tournamentId)
            ?: Ranking(playerId = playerId, tournamentId = tournamentId, totalScore = 0)
        tournamentRanking.totalScore += scoreToAdd
        rankingRepositoryPort.save(tournamentRanking)
    }

    fun getPlayerRanking(playerId: Long): PlayerRankingSummaryResponse {
        val globalRanking = rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId)
        val tournamentRankings = rankingRepositoryPort.findAllByPlayer(playerId).filter { it.tournamentId != null }
        return PlayerRankingSummaryResponse(
            playerId = playerId,
            globalScore = globalRanking?.totalScore ?: 0,
            tournamentScores = tournamentRankings.associate {
                it.tournamentId!! to it.totalScore
            }
        )
    }

}

