package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.application.port.RankingRepositoryPort
import br.com.weconcept.challenge.domain.model.Ranking
import br.com.weconcept.challenge.infrastructure.persistence.repository.RankingJpaRepository
import org.springframework.stereotype.Component

@Component
class RankingRepositoryAdapter(
    private val rankingJpaRepository: RankingJpaRepository
) : RankingRepositoryPort {
    override fun save(ranking: Ranking): Ranking = rankingJpaRepository.save(ranking)
    override fun findByPlayerAndTournamentIsNull(playerId: Long): Ranking? = rankingJpaRepository.findByPlayerIdAndTournamentIdIsNull(playerId)
    override fun findByPlayerAndTournament(playerId: Long, tournamentId: Long): Ranking? = rankingJpaRepository.findByPlayerIdAndTournamentId(playerId, tournamentId)
    override fun findAllByTournamentIsNullOrderByTotalScoreDesc(): List<Ranking> = rankingJpaRepository.findAllByTournamentIdIsNullOrderByTotalScoreDesc()
    override fun findAllByTournamentOrderByTotalScoreDesc(tournamentId: Long): List<Ranking> = rankingJpaRepository.findAllByTournamentIdOrderByTotalScoreDesc(tournamentId)
    override fun findAllByPlayer(playerId: Long): List<Ranking> = rankingJpaRepository.findAllByPlayerId(playerId)
}