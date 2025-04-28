package br.com.weconcept.challenge.infrastructure.web.dto.response

data class RankingResponse(
    val playerId: Long?,
    val totalScore: Int,
    val tournamentId: Long? = null,
    val tournamentScores: Map<Long, Int> = emptyMap()
)
