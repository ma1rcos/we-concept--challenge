package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.*
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChallengeService(
    private val challengeRepository: ChallengeRepositoryPort,
    private val playerRepository: PlayerRepositoryPort,
    private val tournamentRepository: TournamentRepositoryPort,
    private val rankingService: RankingService
) {

    companion object {
        private const val MAX_INPUT_LENGTH = 1000
    }

    @Transactional
    fun executeFibonacciChallenge(
        playerId: Long,
        number: Int,
        tournamentId: Long? = null
    ): ChallengeResult {
        validateInput(number >= 0, Message.FIBONACCI_NEGATIVE)
        validateInput(number <= MAX_INPUT_LENGTH, Message.FIBONACCI_TOO_LARGE.format(MAX_INPUT_LENGTH))

        val player = getPlayer(playerId)
        val tournament = getTournamentIfPresent(tournamentId)
        val result = computeFibonacci(number)

        return logExecution(
            player = player,
            challenge = Challenge.FIBONACCI,
            success = result != null,
            score = if (result != null) Challenge.FIBONACCI.weight else 0,
            result = result,
            tournament = tournament
        )
    }

    @Transactional
    fun executePalindromeChallenge(
        playerId: Long,
        input: String,
        tournamentId: Long? = null
    ): ChallengeResult {
        validateInput(input.isNotBlank(), Message.PALINDROME_EMPTY)
        validateInput(input.length <= MAX_INPUT_LENGTH, Message.INPUT_TOO_LONG.format(MAX_INPUT_LENGTH))

        val player = getPlayer(playerId)
        val tournament = getTournamentIfPresent(tournamentId)
        val result = checkPalindrome(input)

        return logExecution(
            player = player,
            challenge = Challenge.PALINDROME,
            success = true,
            score = if (result) Challenge.PALINDROME.weight else 0,
            result = result,
            tournament = tournament
        )
    }

    @Transactional
    fun executeSortingChallenge(
        playerId: Long,
        numbers: List<Int>,
        tournamentId: Long? = null
    ): ChallengeResult {
        validateInput(numbers.isNotEmpty(), Message.SORTING_EMPTY)
        validateInput(numbers.size <= MAX_INPUT_LENGTH, Message.INPUT_TOO_LARGE.format(MAX_INPUT_LENGTH))

        val player = getPlayer(playerId)
        val tournament = getTournamentIfPresent(tournamentId)
        val result = sortNumbers(numbers)

        return logExecution(
            player = player,
            challenge = Challenge.SORTING,
            success = result.isNotEmpty(),
            score = if (result.isNotEmpty()) Challenge.SORTING.weight else 0,
            result = result,
            tournament = tournament
        )
    }

    @Transactional(readOnly = true)
    fun getPlayerExecutions(playerId: Long): List<ChallengeExecution> {
        return challengeRepository.findExecutionsByPlayer(playerId)
    }

    @Transactional(readOnly = true)
    fun getPlayerTournamentExecutions(playerId: Long, tournamentId: Long): List<ChallengeExecution> {
        return challengeRepository.findExecutionsByPlayerAndTournament(playerId, tournamentId)
    }

    private fun getPlayer(playerId: Long): Player {
        return playerRepository.findById(playerId)
            ?: throw NotFoundException(Message.PLAYER_NOT_FOUND_ID.format(playerId))
    }

    private fun getTournamentIfPresent(tournamentId: Long?): Tournament? {
        return tournamentId?.let {
            tournamentRepository.findById(it)
                ?: throw NotFoundException(Message.TOURNAMENT_NOT_FOUND.format(it))
        }
    }

    private fun validateInput(condition: Boolean, errorMessage: String) {
        if (!condition) throw BadRequestException(errorMessage)
    }

    private fun logExecution(
        player: Player,
        challenge: Challenge,
        success: Boolean,
        score: Int,
        result: Any?,
        tournament: Tournament?
    ): ChallengeResult {
        val execution = ChallengeExecution(
            playerId = player.id,
            challengeId = challenge.id,
            success = success,
            score = score,
            result = result.toString(),
            tournamentId = tournament?.id,
            executedAt = LocalDateTime.now()
        )

        val savedExecution = challengeRepository.saveExecution(execution)

        if (success) {
            rankingService.updatePlayerScore(player.id, score, tournament?.id)
        }

        return ChallengeResult(
            challengeName = challenge.name,
            success = success,
            score = score,
            result = result,
            executionId = savedExecution.id
        )
    }

    private fun computeFibonacci(n: Int): Long? {
        if (n < 0) return null
        if (n == 0) return 0
        if (n == 1) return 1

        var a = 0L
        var b = 1L
        repeat(n - 1) {
            val sum = a + b
            a = b
            b = sum
        }
        return b
    }

    private fun checkPalindrome(input: String): Boolean {
        val clean = input.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        return clean == clean.reversed()
    }

    private fun sortNumbers(list: List<Int>): List<Int> {
        return list.sorted()
    }

}