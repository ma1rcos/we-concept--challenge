package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Challenge
import br.com.weconcept.challenge.domain.model.ChallengeExecution
import br.com.weconcept.challenge.infrastructure.persistence.repository.ChallengeExecutionJpaRepository
import br.com.weconcept.challenge.infrastructure.persistence.repository.ChallengeJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChallengeRepositoryAdapterTest {

    private val challengeJpaRepository = mockk<ChallengeJpaRepository>()
    private val executionJpaRepository = mockk<ChallengeExecutionJpaRepository>()
    private val adapter = ChallengeRepositoryAdapter(challengeJpaRepository, executionJpaRepository)

    @Test
    fun `saveChallenge should delegate to JpaRepository`() {
        val challenge = Challenge(name = "Test", weight = 10)
        every { challengeJpaRepository.save(challenge) } returns challenge
        val result = adapter.saveChallenge(challenge)
        assertEquals(challenge, result)
        verify { challengeJpaRepository.save(challenge) }
    }

    @Test
    fun `saveExecution should save and return execution`() {
        val execution = ChallengeExecution(
            playerId = 1L,
            challengeId = 1L,
            success = true,
            score = 10,
            result = "test"
        )
        every { executionJpaRepository.save(execution) } returns execution
        val result = adapter.saveExecution(execution)
        assertEquals(execution, result)
    }

}