package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.application.port.ChallengeRepositoryPort
import br.com.weconcept.challenge.domain.model.Challenge
import br.com.weconcept.challenge.domain.model.ChallengeExecution
import br.com.weconcept.challenge.infrastructure.persistence.repository.ChallengeExecutionJpaRepository
import br.com.weconcept.challenge.infrastructure.persistence.repository.ChallengeJpaRepository
import org.springframework.stereotype.Component

@Component
class ChallengeRepositoryAdapter(
    private val challengeJpaRepository: ChallengeJpaRepository,
    private val challengeExecutionJpaRepository: ChallengeExecutionJpaRepository
) : ChallengeRepositoryPort {
    override fun saveChallenge(challenge: Challenge): Challenge = challengeJpaRepository.save(challenge)
    override fun findChallengeById(id: Long): Challenge? = challengeJpaRepository.findById(id).orElse(null)
    override fun saveExecution(execution: ChallengeExecution): ChallengeExecution = challengeExecutionJpaRepository.save(execution)
    override fun findExecutionsByPlayer(playerId: Long): List<ChallengeExecution> = challengeExecutionJpaRepository.findByPlayerId(playerId)
    override fun findExecutionsByPlayerAndTournament(playerId: Long, tournamentId: Long): List<ChallengeExecution> = challengeExecutionJpaRepository.findByPlayerIdAndTournamentId(playerId, tournamentId)
}