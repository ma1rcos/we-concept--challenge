package br.com.weconcept.challenge.domain.model

data class ChallengeResult(
    val challengeName: String,
    val success: Boolean,
    val score: Int,
    val result: Any?,
    val executionId: Long
)