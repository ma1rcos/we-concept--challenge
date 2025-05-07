package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.RankingRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.Ranking
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RankingService(
    private val rankingRepositoryPort: RankingRepositoryPort,
    private val playerService: PlayerService
) {

    @Transactional(readOnly = true)
    fun getGlobalRankings(): List<Ranking> =
        rankingRepositoryPort.findAllByTournamentIsNullOrderByTotalScoreDesc()

    @Transactional(readOnly = true)
    fun getTournamentRankings(tournamentId: Long): List<Ranking> =
        rankingRepositoryPort.findAllByTournamentOrderByTotalScoreDesc(tournamentId)

    @Transactional(readOnly = true)
    fun getGlobalRankingForPlayer(playerId: Long): Ranking? {
        playerService.getById(playerId)
        return rankingRepositoryPort.findByPlayerAndTournamentIsNull(playerId)
    }

    @Transactional(readOnly = true)
    fun getTournamentRankingsForPlayer(playerId: Long): List<Ranking> {
        playerService.getById(playerId)
        return rankingRepositoryPort.findAllByPlayer(playerId).filter { it.tournamentId != null }
    }

    @Transactional
    fun updatePlayerScore(
        playerId: Long,
        scoreToAdd: Int,
        tournamentId: Long? = null
    ) {
        require(scoreToAdd >= 0) { throw BadRequestException(Message.INVALID_SCORE_VALUE) }
        playerService.getById(playerId)

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

    @Transactional(readOnly = true)
    fun getPlayerRanking(playerId: Long): PlayerRankingSummaryResponse {
        playerService.getById(playerId)
        val globalRanking = getGlobalRankingForPlayer(playerId)
        val tournamentRankings = getTournamentRankingsForPlayer(playerId)
        return PlayerRankingSummaryResponse(
            playerId = playerId,
            globalScore = globalRanking?.totalScore ?: 0,
            tournamentScores = tournamentRankings.associate {
                it.tournamentId!! to it.totalScore
            }
        )
    }

}