package br.com.weconcept.challenge.infrastructure.web.dto.response

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val status: Int,
    val error: String,
    val timestamp: LocalDateTime
)