package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.TournamentService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.TournamentResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.TournamentMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Tournaments", description = "Operations related to tournament management")
@RestController
@RequestMapping("/tournament")
class TournamentController(
    private val tournamentService: TournamentService,
    private val tournamentMapper: TournamentMapper
) {

    @Operation(
        summary = "Create a new tournament",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(
                    value = """
                    {
                        "name": "Spring Challenge",
                        "date": "2023-05-15"
                    }
                    """
                )]
            )]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Tournament created",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "Spring Challenge",
                            "date": "2023-05-15",
                            "isFinished": false,
                            "players": []
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTournamentRequest): TournamentResponse {
        val tournament = tournamentMapper.toDomain(request)
        val createdTournament = tournamentService.create(tournament)
        return tournamentMapper.toResponse(createdTournament)
    }

    @Operation(
        summary = "Add player to tournament",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player added",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "Spring Challenge",
                            "date": "2023-05-15",
                            "isFinished": false,
                            "players": [
                                {
                                    "id": 1,
                                    "name": "John Doe",
                                    "createdAt": "2023-01-01T12:00:00",
                                    "updatedAt": "2023-01-01T12:00:00"
                                }
                            ]
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Tournament or player not found")
        ]
    )
    @PostMapping("/{tournamentId}/player/{playerId}")
    fun addPlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.addPlayer(tournamentId, playerId)
        return tournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "Remove player from tournament",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player removed",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "Spring Challenge",
                            "date": "2023-05-15",
                            "isFinished": false,
                            "players": []
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Tournament or player not found")
        ]
    )
    @DeleteMapping("/{tournamentId}/player/{playerId}")
    fun removePlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.removePlayer(tournamentId, playerId)
        return tournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "Finish a tournament",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Tournament finished",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "Spring Challenge",
                            "date": "2023-05-15",
                            "isFinished": true,
                            "players": [
                                {
                                    "id": 1,
                                    "name": "John Doe",
                                    "createdAt": "2023-01-01T12:00:00",
                                    "updatedAt": "2023-01-01T12:00:00"
                                },
                                {
                                    "id": 2,
                                    "name": "Jane Smith",
                                    "createdAt": "2023-01-02T10:00:00",
                                    "updatedAt": "2023-01-02T10:00:00"
                                }
                            ]
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Tournament not found")
        ]
    )
    @PostMapping("/{tournamentId}/finish")
    fun finishTournament(@PathVariable tournamentId: Long): TournamentResponse {
        val tournament = tournamentService.finishTournament(tournamentId)
        return tournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "List tournament players",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Players list",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        [
                            {
                                "id": 1,
                                "name": "John Doe",
                                "createdAt": "2023-01-01T12:00:00",
                                "updatedAt": "2023-01-01T12:00:00"
                            },
                            {
                                "id": 2,
                                "name": "Jane Smith",
                                "createdAt": "2023-01-02T10:00:00",
                                "updatedAt": "2023-01-02T10:00:00"
                            }
                        ]
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Tournament not found")
        ]
    )
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