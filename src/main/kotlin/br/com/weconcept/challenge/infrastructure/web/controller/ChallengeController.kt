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

@Tag(name = "Challenge", description = "Endpoints for executing and managing challenges")
@RestController
@RequestMapping("/challenge")
class ChallengeController(
    private val challengeService: ChallengeService
) {

    @Operation(
        summary = "Execute Fibonacci challenge",
        description = "Calculate the nth Fibonacci number for the given input."
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
        ApiResponse(responseCode = "400", description = "Invalid input provided"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/fibonacci")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeFibonacci(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Fibonacci challenge request details",
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
        @RequestBody request: FibonacciChallengeRequest,
        @Parameter(description = "Optional ID of the tournament", example = "1")
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
        description = "Check if the provided string is a palindrome (case-insensitive, ignoring spaces and special characters)."
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
                        "challengeName": "Palindrome",
                        "success": true,
                        "score": 5,
                        "result": true,
                        "executionId": 2
                    }
                    """
                )]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input provided"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/palindrome")
    @ResponseStatus(HttpStatus.CREATED)
    fun executePalindrome(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Palindrome challenge request details",
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
        @RequestBody request: PalindromeChallengeRequest,
        @Parameter(description = "Optional ID of the tournament", example = "1")
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
        description = "Sort an array of integers using a merge sort algorithm."
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
                        "challengeName": "Sorting",
                        "success": true,
                        "score": 8,
                        "result": [1, 2, 3, 4, 5],
                        "executionId": 3
                    }
                    """
                )]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input provided"),
        ApiResponse(responseCode = "404", description = "Player not found")
    ])
    @PostMapping("/sorting")
    @ResponseStatus(HttpStatus.CREATED)
    fun executeSorting(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sorting challenge request details",
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
        @RequestBody request: SortingChallengeRequest,
        @Parameter(description = "Optional ID of the tournament", example = "1")
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
