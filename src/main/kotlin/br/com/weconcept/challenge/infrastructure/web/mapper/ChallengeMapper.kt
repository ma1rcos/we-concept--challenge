package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.ChallengeResult
import br.com.weconcept.challenge.infrastructure.web.dto.response.ChallengeResponse
import org.springframework.stereotype.Component

@Component
class ChallengeMapper {

    fun toResponse(result: ChallengeResult): ChallengeResponse {
        return ChallengeResponse(
            challengeName = result.challengeName,
            success = result.success,
            score = result.score,
            result = result.result,
            executionId = result.executionId
        )
    }
}