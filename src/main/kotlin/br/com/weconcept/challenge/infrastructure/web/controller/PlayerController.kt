package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.request.UpdatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.bind.annotation.*

@Tag(name = "Player", description = "Operations related to players management")
@RestController
@RequestMapping("/player")
class PlayerController(
    private val playerService: PlayerService
) {

    @Operation(
        summary = "Create a new player",
        description = "Registers a new player in the system."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Player created successfully.",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PlayerResponse::class),
                examples = [ExampleObject(value = """
                {
                    "id": 1,
                    "name": "John Doe",
                    "createdAt": "2023-01-01T12:00:00",
                    "updatedAt": "2023-01-01T12:00:00"
                }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input data.")
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the player to create.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                {
                    "name": "John Doe"
                }
                """)]
            )]
        )
        @RequestBody request: CreatePlayerRequest
    ): PlayerResponse {
        val player = PlayerMapper.toDomain(request)
        val createdPlayer = playerService.create(player)
        return PlayerMapper.toResponse(createdPlayer)
    }

    @Operation(
        summary = "Get player by ID",
        description = "Retrieves player details using their unique ID."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player details retrieved successfully.",
            content = [Content(
                schema = Schema(implementation = PlayerResponse::class)
            )]
        ),
        ApiResponse(responseCode = "404", description = "Player not found.")
    ])
    @GetMapping("/{id}")
    fun getById(
        @Parameter(description = "Unique ID of the player to retrieve.", example = "1")
        @PathVariable id: Long
    ): PlayerResponse = playerService.getById(id)?.let { PlayerMapper.toResponse(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Operation(
        summary = "Get player by name",
        description = "Retrieves player details by their exact name (case-sensitive)."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player details retrieved successfully.",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PlayerResponse::class),
                examples = [ExampleObject(value = """
                {
                    "id": 1,
                    "name": "John Doe",
                    "createdAt": "2023-01-01T12:00:00",
                    "updatedAt": "2023-01-01T12:00:00"
                }
                """)]
            )]
        ),
        ApiResponse(responseCode = "404", description = "Player not found.")
    ])
    @GetMapping
    fun getByName(
        @Parameter(
            description = "Exact name of the player to search for.",
            example = "John Doe",
            required = true
        )
        @RequestParam name: String
    ): PlayerResponse = playerService.getByName(name)?.let { PlayerMapper.toResponse(it) } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Operation(
        summary = "Update player information",
        description = "Updates the details of an existing player."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player details updated successfully.",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PlayerResponse::class),
                examples = [ExampleObject(value = """
                {
                    "id": 1,
                    "name": "Updated Name",
                    "createdAt": "2023-01-01T12:00:00",
                    "updatedAt": "2023-01-15T14:30:00"
                }
                """)]
            )]
        ),
        ApiResponse(responseCode = "400", description = "Invalid input data."),
        ApiResponse(responseCode = "404", description = "Player not found.")
    ])
    @PutMapping("/{id}")
    fun update(
        @Parameter(description = "Unique ID of the player to update.", example = "1")
        @PathVariable id: Long,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated player details.",
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(value = """
                {
                    "name": "Updated Name"
                }
                """)]
            )]
        )
        @RequestBody request: UpdatePlayerRequest
    ): PlayerResponse {
        val existingPlayer = playerService.getById(id) ?: throw IllegalArgumentException("Player not found.")
        val updatedPlayer = existingPlayer.copy(name = request.name)
        val result = playerService.update(updatedPlayer)
        return PlayerMapper.toResponse(result)
    }

    @Operation(
        summary = "Delete a player",
        description = "Permanently deletes a player using their unique ID."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Player deleted successfully."),
        ApiResponse(responseCode = "404", description = "Player not found.")
    ])
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(
        @Parameter(description = "Unique ID of the player to delete.", example = "1")
        @PathVariable id: Long
    ) = playerService.deleteById(id)
}
