package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request payload for creating a player")
data class CreatePlayerRequest(
    @Schema(
        description = "Data for the player",
        example = "Player Name",
        required = true,
        minLength = 3,
        maxLength = 50
    )
    val name: String
)