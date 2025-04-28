package br.com.weconcept.challenge.infrastructure.web.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Request payload for creating a tournament")
data class CreateTournamentRequest(
    @Schema(
        description = "Name of the tournament",
        example = "Spring Challenge",
        required = true,
        minLength = 3,
        maxLength = 100
    )
    val name: String,
    @Schema(
        description = "Date of the tournament",
        example = "2023-05-15",
        required = true
    )
    val date: LocalDate
)