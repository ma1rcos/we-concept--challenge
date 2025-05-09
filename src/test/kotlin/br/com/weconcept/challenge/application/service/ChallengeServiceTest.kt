package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.ChallengeRepositoryPort
import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
    fun `executeFibonacciChallenge should throw BadRequestException for negative number`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player

        val exception = assertThrows<BadRequestException> {
            challengeService.executeFibonacciChallenge(1L, -1)
        }
        assertEquals(Message.FIBONACCI_NEGATIVE, exception.message)
    }

    @Test
    fun `executeFibonacciChallenge should throw BadRequestException for number too large`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player

        val exception = assertThrows<BadRequestException> {
            challengeService.executeFibonacciChallenge(1L, 1001)
        }
        assertEquals(Message.FIBONACCI_TOO_LARGE.format(1000), exception.message)
    }

    @Test
    fun `executeFibonacciChallenge should throw NotFoundException when player not found`() {
        every { playerRepositoryPort.findById(1L) } returns null

        val exception = assertThrows<NotFoundException> {
            challengeService.executeFibonacciChallenge(1L, 10)
        }
        assertEquals(Message.PLAYER_NOT_FOUND_ID.format(1L), exception.message)
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
    fun `executePalindromeChallenge should throw BadRequestException for empty input`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player

        val exception = assertThrows<BadRequestException> {
            challengeService.executePalindromeChallenge(1L, "")
        }
        assertEquals(Message.PALINDROME_EMPTY, exception.message)
    }

    @Test
    fun `executePalindromeChallenge should throw BadRequestException for input too long`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        val longInput = "a".repeat(1001)

        val exception = assertThrows<BadRequestException> {
            challengeService.executePalindromeChallenge(1L, longInput)
        }
        assertEquals(Message.INPUT_TOO_LONG.format(1000), exception.message)
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

    @Test
    fun `executeSortingChallenge should throw BadRequestException for empty list`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player

        val exception = assertThrows<BadRequestException> {
            challengeService.executeSortingChallenge(1L, emptyList())
        }
        assertEquals(Message.SORTING_EMPTY, exception.message)
    }

    @Test
    fun `executeSortingChallenge should throw BadRequestException for list too large`() {
        val player = Player(id = 1L, name = "Test Player")
        every { playerRepositoryPort.findById(1L) } returns player
        val largeList = List(1001) { it }

        val exception = assertThrows<BadRequestException> {
            challengeService.executeSortingChallenge(1L, largeList)
        }
        assertEquals(Message.INPUT_TOO_LARGE.format(1000), exception.message)
    }

    @Test
    fun `getPlayerExecutions should return executions`() {
        val executions = listOf(
            ChallengeExecution(
                playerId = 1L,
                challengeId = 1L,
                success = true,
                score = 10,
                result = "55"
            )
        )
        every { challengeRepositoryPort.findExecutionsByPlayer(1L) } returns executions

        val result = challengeService.getPlayerExecutions(1L)
        assertEquals(1, result.size)
        assertEquals(10, result[0].score)
    }

    @Test
    fun `getPlayerTournamentExecutions should return tournament executions`() {
        val executions = listOf(
            ChallengeExecution(
                playerId = 1L,
                challengeId = 1L,
                success = true,
                score = 10,
                result = "55",
                tournamentId = 1L
            )
        )
        every { challengeRepositoryPort.findExecutionsByPlayerAndTournament(1L, 1L) } returns executions

        val result = challengeService.getPlayerTournamentExecutions(1L, 1L)
        assertEquals(1, result.size)
        assertEquals(1L, result[0].tournamentId)
    }

}