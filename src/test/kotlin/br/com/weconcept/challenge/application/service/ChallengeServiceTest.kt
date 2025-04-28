package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.ChallengeRepositoryPort
import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.model.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChallengeServiceTest {

    private val challengeRepositoryPort = mockk<ChallengeRepositoryPort>()
    private val playerRepositoryPort = mockk<PlayerRepositoryPort>()
    private val tournamentRepositoryPort = mockk<TournamentRepositoryPort>()
    private val rankingService = mockk<RankingService>()

    private val challengeService = ChallengeService(
        challengeRepositoryPort,
        playerRepositoryPort,
        tournamentRepositoryPort,
        rankingService
    )

    @Test
    fun `executeFibonacciChallenge should return successful result for valid input`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        every { challengeRepositoryPort.saveExecution(any()) } returns ChallengeExecution(
            id = 1L,
            playerId = 1L,
            challengeId = 1L,
            success = true,
            score = 10,
            result = "55"
        )
        every { rankingService.updatePlayerScore(any(), any(), any()) } returns Unit
        val result = challengeService.executeFibonacciChallenge(1L, 10)
        assertTrue(result.success)
        assertEquals(10, result.score)
        assertEquals(55L, result.result)
        verify { rankingService.updatePlayerScore(1L, 10, null) }
    }

    @Test
    fun `executePalindromeChallenge should return correct result`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        every { challengeRepositoryPort.saveExecution(any()) } returns ChallengeExecution(
            id = 1L,
            playerId = 1L,
            challengeId = 2L,
            success = true,
            score = 5,
            result = "true"
        )
        every { rankingService.updatePlayerScore(any(), any(), any()) } returns Unit
        val result = challengeService.executePalindromeChallenge(1L, "madam")
        assertTrue(result.success)
        assertEquals(true, result.result)
    }

    @Test
    fun `executeSortingChallenge should return sorted array`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        every { challengeRepositoryPort.saveExecution(any()) } returns ChallengeExecution(
            id = 1L,
            playerId = 1L,
            challengeId = 3L,
            success = true,
            score = 8,
            result = "[1, 2, 3]"
        )
        every { rankingService.updatePlayerScore(any(), any(), any()) } returns Unit
        val result = challengeService.executeSortingChallenge(1L, listOf(3, 1, 2))
        assertEquals(listOf(1, 2, 3), result.result)
    }
}