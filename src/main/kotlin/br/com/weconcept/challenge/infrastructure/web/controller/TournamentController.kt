package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.TournamentService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.ErrorResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.TournamentResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.TournamentMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Tag(name = "Tournament", description = "Operations for managing tournaments.")
@RestController
@RequestMapping("/tournament")
class TournamentController(
    private val tournamentService: TournamentService
) {

    @Operation(
        summary = "Create a new tournament",
        description = "Creates a new tournament with the specified details."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Tournament created successfully.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                {
                    "id": 1,
                    "name": "Spring Challenge",
                    "date": "2023-05-15",
                    "isFinished": false,
                    "players": []
                }
            """)]
            )]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input or tournament name already exists.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                {
                    "message": "Já existe um torneio com este nome",
                    "status": 400,
                    "error": "Bad Request",
                    "timestamp": "2023-05-15T12:00:00"
                }
            """)]
            )]
        )
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreateTournamentRequest): ResponseEntity<Any> {
        return try {
            val tournament = TournamentMapper.toDomain(request)
            val createdTournament = tournamentService.create(tournament)
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TournamentMapper.toResponse(createdTournament))
        } catch (e: IllegalArgumentException) {
            ResponseEntity
                .badRequest()
                .body(
                    ErrorResponse(
                        message = e.message ?: "Nome do torneio já existe",
                        status = HttpStatus.BAD_REQUEST.value(),
                        error = HttpStatus.BAD_REQUEST.reasonPhrase,
                        timestamp = LocalDateTime.now()
                    )
                )
        }
    }

    @Operation(
        summary = "Add a player to a tournament",
        description = "Associates an existing player with a specified tournament."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player added successfully.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "id": 1,
                        "name": "Spring Challenge",
                        "date": "2023-05-15",
                        "isFinished": false,
                        "players": [
                            {
                                "id": 1,
                                "name": "John Doe",
                                "createdAt": "2023-04-01T10:00:00",
                                "updatedAt": "2023-04-01T10:00:00"
                            }
                        ]
                    }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input."),
        ApiResponse(responseCode = "404", description = "Tournament or player not found.")
    ])
    @PostMapping("/{tournamentId}/player/{playerId}")
    fun addPlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.addPlayer(tournamentId, playerId)
        return TournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "Remove a player from a tournament",
        description = "Disassociates a player from a specified tournament."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player removed successfully.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "id": 1,
                        "name": "Spring Challenge",
                        "date": "2023-05-15",
                        "isFinished": false,
                        "players": []
                    }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input."),
        ApiResponse(responseCode = "404", description = "Tournament or player not found.")
    ])
    @DeleteMapping("/{tournamentId}/player/{playerId}")
    fun removePlayer(
        @PathVariable tournamentId: Long,
        @PathVariable playerId: Long
    ): TournamentResponse {
        val tournament = tournamentService.removePlayer(tournamentId, playerId)
        return TournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "Finish a tournament",
        description = "Marks the specified tournament as finished."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Tournament finished successfully.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    {
                        "id": 1,
                        "name": "Spring Challenge",
                        "date": "2023-05-15",
                        "isFinished": true,
                        "players": [
                            {
                                "id": 1,
                                "name": "John Doe",
                                "createdAt": "2023-04-01T10:00:00",
                                "updatedAt": "2023-04-01T10:00:00"
                            }
                        ]
                    }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input."),
        ApiResponse(responseCode = "404", description = "Tournament not found.")
    ])
    @PostMapping("/{tournamentId}/finish")
    fun finishTournament(
        @PathVariable tournamentId: Long
    ): TournamentResponse {
        val tournament = tournamentService.finishTournament(tournamentId)
        return TournamentMapper.toResponse(tournament)
    }

    @Operation(
        summary = "List players in a tournament",
        description = "Fetches a list of all players in a specified tournament."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Players retrieved successfully.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                    [
                        {
                            "id": 1,
                            "name": "John Doe",
                            "createdAt": "2023-04-01T10:00:00",
                            "updatedAt": "2023-04-01T10:00:00"
                        },
                        {
                            "id": 2,
                            "name": "Jane Smith",
                            "createdAt": "2023-04-02T11:00:00",
                            "updatedAt": "2023-04-02T11:00:00"
                        }
                    ]
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input."),
        ApiResponse(responseCode = "404", description = "Tournament not found.")
    ])
    @GetMapping("/{tournamentId}/player")
    fun listPlayers(
        @PathVariable tournamentId: Long
    ): List<PlayerResponse> {
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
