package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.TournamentResponse
import org.springframework.stereotype.Component

@Component
class TournamentMapper {

    fun toDomain(request: CreateTournamentRequest): Tournament {
        return Tournament(
            name = request.name,
            date = request.date
        )
    }

    fun toResponse(tournament: Tournament): TournamentResponse {
        return TournamentResponse(
            id = tournament.id,
            name = tournament.name,
            date = tournament.date,
            isFinished = tournament.isFinished,
            players = tournament.players.map {
                PlayerResponse(
                    id = it.id,
                    name = it.name,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
        )
    }

}