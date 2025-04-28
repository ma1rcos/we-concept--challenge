package br.com.weconcept.challenge.infrastructure.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Complete player ranking summary")
data class PlayerRankingSummaryResponse(
    @Schema(
        description = "ID of the player",
        example = "1",
        required = true
    )
    val playerId: Long,
    @Schema(
        description = "Player's global accumulated score",
        example = "330",
        required = true
    )
    val globalScore: Int,
    @Schema(
        description = "Map of tournament IDs to scores",
        example = "{\"1\": 180, \"2\": 150}",
        required = true
    )
    val tournamentScores: Map<Long, Int>
)