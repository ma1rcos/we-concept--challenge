package br.com.weconcept.challenge.infrastructure.web.dto.request

data class SortingChallengeRequest(
    val playerId: Long,
    val numbers: List<Int>
)