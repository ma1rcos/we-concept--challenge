package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.ChallengeService
import br.com.weconcept.challenge.infrastructure.web.dto.request.*
import br.com.weconcept.challenge.infrastructure.web.dto.response.ChallengeResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.ChallengeMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Challenger", description = "Operations related to challengers management")
@RestController
@RequestMapping("/challenge")
class ChallengeController(
    private val challengeService: ChallengeService
) {

    @Operation(
        summary = "Execute Fibonacci challenge",
        description = "Calculates the nth number in the Fibonacci sequence"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Challenge executed successfully",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(
                    value = """
                    {
                        "challengeName": "Fibonacci",
                        "success": true,
                        "score": 10,
                        "result": 55,
                        "executionId": 1
                    }
                """
                )]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/fibonacci")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeFibonacci(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Fibonacci challenge request",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "playerId": 1,
                        "number": 10
                    }
                """)]
            )]
        )
        @Parameter(
            description = "Optional tournament ID to associate with the challenge",
            example = "1"
        )
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

    @Operation(
        summary = "Execute Palindrome challenge",
        description = "Validates if a string is a palindrome (ignores case, spaces and special characters)"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Challenge executed successfully",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "challengeName": "Palindrome",
                        "success": true,
                        "score": 5,
                        "result": true,
                        "executionId": 2
                    }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/palindrome")
    @ResponseStatus(HttpStatus.CREATED)
    fun executePalindrome(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Palindrome challenge request",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "playerId": 1,
                        "input": "A man a plan a canal Panama"
                    }
                """)]
            )]
        )
        @Parameter(
            description = "Optional tournament ID to associate with the challenge",
            example = "1"
        )
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

    @Operation(
        summary = "Execute Sorting challenge",
        description = "Sorts an array of numbers using a custom merge sort algorithm"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Challenge executed successfully",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "challengeName": "Sorting",
                        "success": true,
                        "score": 8,
                        "result": [1, 2, 3, 4, 5],
                        "executionId": 3
                    }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/sorting")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeSorting(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sorting challenge request",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "playerId": 1,
                        "numbers": [5, 3, 1, 4, 2]
                    }
                """)]
            )]
        )
        @Parameter(
            description = "Optional tournament ID to associate with the challenge",
            example = "1"
        )
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