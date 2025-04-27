package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.TournamentService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
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
    fun createTournament(@RequestBody request: CreateTournamentRequest): TournamentResponse {
        val tournament = TournamentMapper.toDomain(request)
        val createdTournament = tournamentService.create(tournament)
        return TournamentMapper.toResponse(createdTournament)
    }

}