package br.com.weconcept.challenge.infrastructure.web.dto.request

import java.time.LocalDate

data class CreateTournamentRequest(
    val name: String,
    val date: LocalDate
)