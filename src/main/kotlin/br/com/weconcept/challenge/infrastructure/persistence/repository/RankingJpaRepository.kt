package br.com.weconcept.challenge.infrastructure.persistence.repository

import br.com.weconcept.challenge.domain.model.Ranking
import org.springframework.data.jpa.repository.JpaRepository

interface RankingJpaRepository : JpaRepository<Ranking, Long> {

    fun findByPlayerIdAndTournamentIdIsNull(playerId: Long): Ranking?

    fun findByPlayerIdAndTournamentId(playerId: Long, tournamentId: Long): Ranking?

    fun findAllByTournamentIdIsNullOrderByTotalScoreDesc(): List<Ranking>

    fun findAllByTournamentIdOrderByTotalScoreDesc(tournamentId: Long): List<Ranking>

    fun findAllByPlayerId(playerId: Long): List<Ranking>

}