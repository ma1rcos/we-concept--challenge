package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.ChallengeService
import br.com.weconcept.challenge.infrastructure.web.dto.request.*
import br.com.weconcept.challenge.infrastructure.web.dto.response.ChallengeResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.ChallengeMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/challenge")
class ChallengeController(
    private val challengeService: ChallengeService
) {

    @PostMapping("/fibonacci")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeFibonacci(
        @RequestBody request: FibonacciChallengeRequest,
        @RequestParam(required = false) tournamentId: Long?
    ): ChallengeResponse {
        val result = challengeService.executeFibonacciChallenge(
            request.playerId,
            request.number,
            tournamentId
        )
        return ChallengeMapper.toResponse(result)
    }

    @PostMapping("/palindrome")
    @ResponseStatus(HttpStatus.CREATED)
    fun executePalindrome(
        @RequestBody request: PalindromeChallengeRequest,
        @RequestParam(required = false) tournamentId: Long?
    ): ChallengeResponse {
        val result = challengeService.executePalindromeChallenge(
            request.playerId,
            request.input,
            tournamentId
        )
        return ChallengeMapper.toResponse(result)
    }

    @PostMapping("/sorting")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeSorting(
        @RequestBody request: SortingChallengeRequest,
        @RequestParam(required = false) tournamentId: Long?
    ): ChallengeResponse {
        val result = challengeService.executeSortingChallenge(
            request.playerId,
            request.numbers,
            tournamentId
        )
        return ChallengeMapper.toResponse(result)
    }

}