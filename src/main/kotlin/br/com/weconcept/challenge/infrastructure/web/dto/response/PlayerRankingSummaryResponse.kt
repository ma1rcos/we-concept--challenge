package br.com.weconcept.challenge.infrastructure.web.dto.response

data class PlayerRankingSummaryResponse(
    val playerId: Long,
    val globalScore: Int,
    val tournamentScores: Map<Long, Int>
)
