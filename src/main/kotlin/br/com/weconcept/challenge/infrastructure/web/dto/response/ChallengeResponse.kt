package br.com.weconcept.challenge.infrastructure.web.dto.response

data class ChallengeResponse(
    val challengeName: String,
    val success: Boolean,
    val score: Int,
    val result: Any?,
    val executionId: Long
)