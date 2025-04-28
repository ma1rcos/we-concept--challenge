package br.com.weconcept.challenge.infrastructure.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Ranking entry response")
data class RankingResponse(
    @Schema(
        description = "ID of the player",
        example = "1",
        required = true
    )
    val playerId: Long,
    @Schema(
        description = "Total score points",
        example = "150",
        required = true
    )
    val totalScore: Int,
    @Schema(
        description = "ID of the tournament (null for global ranking)",
        example = "1",
        required = false
    )
    val tournamentId: Long? = null
)