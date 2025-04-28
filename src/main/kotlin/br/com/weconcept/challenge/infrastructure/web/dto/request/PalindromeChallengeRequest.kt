package br.com.weconcept.challenge.infrastructure.web.dto.request

data class PalindromeChallengeRequest(
    val playerId: Long,
    val input: String
)
