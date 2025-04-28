package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload for sorting challenge")
data class SortingChallengeRequest(
    @Schema(
        description = "ID of the player executing the challenge",
        example = "1",
        required = true
    )
    val playerId: Long,

    @Schema(
        description = "Array of numbers to be sorted",
        example = "[5, 3, 1, 4, 2]",
        required = true
    )
    val numbers: List<Int>
)