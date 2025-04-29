package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.ChallengeRepositoryPort
import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.model.Challenge
import br.com.weconcept.challenge.domain.model.ChallengeExecution
import br.com.weconcept.challenge.domain.model.ChallengeResult
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChallengeService(
    private val challengeRepositoryPort: ChallengeRepositoryPort,
    private val playerRepositoryPort: PlayerRepositoryPort,
    private val tournamentRepositoryPort: TournamentRepositoryPort,
    private val rankingService: RankingService
) {

    @Transactional
    fun executeFibonacciChallenge(
        playerId: Long, 
        number: Int, 
        tournamentId: Long? = null
    ): ChallengeResult {
        val player = playerRepositoryPort.findById(playerId) ?: throw IllegalArgumentException("Player not found")
        val tournament = tournamentId?.let { tournamentRepositoryPort.findById(it) }
        val result = calculateFibonacci(number)
        val challenge = Challenge.FIBONACCI
        return registerChallengeExecution(
            player = player,
            challenge = challenge,
            success = result != null,
            score = if (result != null) challenge.weight else 0,
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
        val player = playerRepositoryPort.findById(playerId) ?: throw IllegalArgumentException("Player not found")
        val tournament = tournamentId?.let { tournamentRepositoryPort.findById(it) }
        val result = isPalindrome(input)
        val challenge = Challenge.PALINDROME
        return registerChallengeExecution(
            player = player,
            challenge = challenge,
            success = true,
            score = if (result) challenge.weight else 0,
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
        val player = playerRepositoryPort.findById(playerId) ?: throw IllegalArgumentException("Player not found")
        val tournament = tournamentId?.let { tournamentRepositoryPort.findById(it) }
        val result = customSort(numbers)
        val challenge = Challenge.SORTING
        return registerChallengeExecution(
            player = player,
            challenge = challenge,
            success = result.isNotEmpty(),
            score = if (result.isNotEmpty()) challenge.weight else 0,
            result = result,
            tournament = tournament
        )
    }

    private fun registerChallengeExecution(
        player: Player,
        challenge: Challenge,
        success: Boolean,
        score: Int,
        result: Any?,
        tournament: Tournament? = null
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
        val savedExecution = challengeRepositoryPort.saveExecution(execution)
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

    private fun calculateFibonacci(n: Int): Long? {
        if (n < 0) return null
        if (n == 0) return 0
        if (n == 1) return 1
        var a = 0L
        var b = 1L
        var result = 0L
        for (i in 2..n) {
            result = a + b
            a = b
            b = result
        }
        return result
    }

    private fun isPalindrome(input: String): Boolean {
        val cleaned = input.replace("[^a-zA-Z0-9]".toRegex(), "").lowercase()
        return cleaned == cleaned.reversed()
    }

    private fun customSort(array: List<Int>): List<Int> {
        if (array.size <= 1) return array
        fun merge(left: List<Int>, right: List<Int>): List<Int> {
            var indexLeft = 0
            var indexRight = 0
            val newList = mutableListOf<Int>()
            while (indexLeft < left.size && indexRight < right.size) {
                if (left[indexLeft] <= right[indexRight]) {
                    newList.add(left[indexLeft])
                    indexLeft++
                } else {
                    newList.add(right[indexRight])
                    indexRight++
                }
            }
            while (indexLeft < left.size) {
                newList.add(left[indexLeft])
                indexLeft++
            }
            while (indexRight < right.size) {
                newList.add(right[indexRight])
                indexRight++
            }
            return newList
        }
        val middle = array.size / 2
        val left = array.subList(0, middle)
        val right = array.subList(middle, array.size)
        return merge(customSort(left), customSort(right))
    }

}