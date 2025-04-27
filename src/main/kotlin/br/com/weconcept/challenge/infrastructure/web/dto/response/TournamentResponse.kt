package br.com.weconcept.challenge.infrastructure.web.dto.response

import java.time.LocalDate

data class TournamentResponse(
    val id: Long,
    val name: String,
    val date: LocalDate,
    val isFinished: Boolean,
    val players: List<PlayerResponse>
)