package br.com.weconcept.challenge.infrastructure.web.dto.response

import java.time.LocalDateTime

data class PlayerResponse(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)