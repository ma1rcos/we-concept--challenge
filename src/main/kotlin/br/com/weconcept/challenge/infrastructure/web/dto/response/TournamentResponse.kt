package br.com.weconcept.challenge.infrastructure.web.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Tournament response data")
data class TournamentResponse(
    @Schema(
        description = "ID of the tournament",
        example = "1",
        required = true
    )
    val id: Long,
    @Schema(
        description = "Name of the tournament",
        example = "Spring Challenge",
        required = true
    )
    val name: String,
    @Schema(
        description = "Date of the tournament",
        example = "2023-05-15",
        required = true
    )
    val date: LocalDate,
    @Schema(
        description = "Indicates if the tournament is finished",
        example = "false",
        required = true
    )
    val isFinished: Boolean,
    @Schema(
        description = "List of players registered in the tournament",
        required = true
    )
    val players: List<PlayerResponse>
)