package br.com.weconcept.challenge.infrastructure.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Player information response")
data class PlayerResponse(
    @Schema(description = "Unique identifier of the player", example = "1")
    val id: Long,
    @Schema(description = "Name of the player", example = "John Doe")
    val name: String,
    @Schema(
        description = "Timestamp when player was created",
        example = "2023-01-01T12:00:00"
    )
    val createdAt: LocalDateTime,
    @Schema(
        description = "Timestamp when player was last updated",
        example = "2023-01-01T12:00:00"
    )
    val updatedAt: LocalDateTime
)