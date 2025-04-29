package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Challenge
import br.com.weconcept.challenge.domain.model.ChallengeExecution

interface ChallengeRepositoryPort {

    fun saveChallenge(challenge: Challenge): Challenge
    
    fun findChallengeById(id: Long): Challenge?
    
    fun saveExecution(execution: ChallengeExecution): ChallengeExecution
    
    fun findExecutionsByPlayer(playerId: Long): List<ChallengeExecution>
    
    fun findExecutionsByPlayerAndTournament(playerId: Long, tournamentId: Long): List<ChallengeExecution>

}