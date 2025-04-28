package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload for palindrome challenge")
data class PalindromeChallengeRequest(
    @Schema(
        description = "ID of the player executing the challenge",
        example = "1",
        required = true
    )
    val playerId: Long,
    @Schema(
        description = "Text to be checked as palindrome",
        example = "A man a plan a canal Panama",
        required = true
    )
    val input: String
)
