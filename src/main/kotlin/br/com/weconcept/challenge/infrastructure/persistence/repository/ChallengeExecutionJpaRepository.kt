package br.com.weconcept.challenge.infrastructure.persistence.repository

import br.com.weconcept.challenge.domain.model.ChallengeExecution
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeExecutionJpaRepository : JpaRepository<ChallengeExecution, Long> {
    fun findByPlayerId(playerId: Long): List<ChallengeExecution>
    fun findByPlayerIdAndTournamentId(playerId: Long, tournamentId: Long): List<ChallengeExecution>
}