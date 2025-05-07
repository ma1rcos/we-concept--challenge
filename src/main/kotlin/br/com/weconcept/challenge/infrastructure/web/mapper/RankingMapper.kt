package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Ranking
import br.com.weconcept.challenge.infrastructure.web.dto.response.RankingResponse
import org.springframework.stereotype.Component

@Component
class RankingMapper {

    fun toResponse(ranking: Ranking): RankingResponse {
        return RankingResponse(
            playerId = ranking.playerId,
            totalScore = ranking.totalScore,
            tournamentId = ranking.tournamentId
        )
    }

}
