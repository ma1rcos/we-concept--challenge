package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload for updating a player")
data class UpdatePlayerRequest(
    @Schema(
        description = "New name for the player",
        example = "Updated Player Name",
        required = true,
        minLength = 3,
        maxLength = 50
    )
    val name: String
)