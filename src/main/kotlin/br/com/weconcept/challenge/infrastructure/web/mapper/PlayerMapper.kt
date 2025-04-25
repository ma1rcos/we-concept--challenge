package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse

object PlayerMapper {

    fun toDomain(request: CreatePlayerRequest): Player {
        return Player(
            name = request.name
        )
    }

    fun toResponse(player: Player): PlayerResponse {
        return PlayerResponse(
            id = player.id,
            name = player.name,
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )
    }

}