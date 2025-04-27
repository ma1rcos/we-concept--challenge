package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.TournamentService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.TournamentResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.TournamentMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tournament")
class TournamentController(
    private val tournamentService: TournamentService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTournamentRequest): TournamentResponse {
        val tournament = TournamentMapper.toDomain(request)
        val createdTournament = tournamentService.create(tournament)
        return TournamentMapper.toResponse(createdTournament)
    }

    @PostMapping("/{tournamentId}/player/{playerId}")
    fun addPlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.addPlayer(tournamentId, playerId)
        return TournamentMapper.toResponse(tournament)
    }

    @DeleteMapping("/{tournamentId}/player/{playerId}")
    fun removePlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.removePlayer(tournamentId, playerId)
        return TournamentMapper.toResponse(tournament)
    }

    @PostMapping("/{tournamentId}/finish")
    fun finishTournament(@PathVariable tournamentId: Long): TournamentResponse {
        val tournament = tournamentService.finishTournament(tournamentId)
        return TournamentMapper.toResponse(tournament)
    }

    @GetMapping("/{tournamentId}/player")
    fun listPlayers(@PathVariable tournamentId: Long): List<PlayerResponse> {
        return tournamentService.listPlayers(tournamentId)
            .map { player ->
                PlayerResponse(
                    id = player.id,
                    name = player.name,
                    createdAt = player.createdAt,
                    updatedAt = player.updatedAt
                )
            }
    }


}