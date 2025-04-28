package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload for Fibonacci challenge")
data class FibonacciChallengeRequest(
    @Schema(
        description = "ID of the player executing the challenge",
        example = "1",
        required = true
    )
    val playerId: Long,
    @Schema(
        description = "The index in the Fibonacci sequence to calculate (0-based)",
        example = "10",
        required = true,
        minimum = "0",
        maximum = "1000"
    )
    val number: Int
)