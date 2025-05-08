package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.request.UpdatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Players", description = "Operations related to player management")
@RestController
@RequestMapping("/player")
class PlayerController(
    private val playerService: PlayerService,
    private val playerMapper: PlayerMapper
) {

    @Operation(
        summary = "Create a new player",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(
                    value = """
                    {
                        "name": "John Doe"
                    }
                    """
                )]
            )]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Player created successfully",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "John Doe",
                            "createdAt": "2023-01-01T12:00:00",
                            "updatedAt": "2023-01-01T12:00:00"
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
    fun create(@RequestBody request: CreatePlayerRequest): PlayerResponse {
        val player = playerMapper.toDomain(request)
        val createdPlayer = playerService.create(player)
        return playerMapper.toResponse(createdPlayer)
    }

    @Operation(
        summary = "Get player by ID",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player found",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "John Doe",
                            "createdAt": "2023-01-01T12:00:00",
                            "updatedAt": "2023-01-01T12:00:00"
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Player not found")
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): PlayerResponse {
        val player = playerService.getById(id)
        return playerMapper.toResponse(player)
    }

    @Operation(
        summary = "Get player by name",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player found",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "John Doe",
                            "createdAt": "2023-01-01T12:00:00",
                            "updatedAt": "2023-01-01T12:00:00"
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Player not found")
        ]
    )
    @GetMapping
    fun getByName(@RequestParam name: String): PlayerResponse {
        val player = playerService.getByName(name)
        return playerMapper.toResponse(player)
    }

    @Operation(
        summary = "Update player information",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                mediaType = "application/json",
                examples = [ExampleObject(
                    value = """
                    {
                        "name": "Updated Player Name"
                    }
                    """
                )]
            )]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player updated",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "id": 1,
                            "name": "Updated Player Name",
                            "createdAt": "2023-01-01T12:00:00",
                            "updatedAt": "2023-01-02T10:30:00"
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Player not found")
        ]
    )
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdatePlayerRequest
    ): PlayerResponse {
        val existingPlayer = playerService.getById(id)
        val updatedPlayer = existingPlayer.copy(name = request.name)
        val result = playerService.update(updatedPlayer)
        return playerMapper.toResponse(result)
    }

    @Operation(
        summary = "Delete a player",
        responses = [
            ApiResponse(responseCode = "204", description = "Player deleted"),
            ApiResponse(responseCode = "404", description = "Player not found")
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) {
        playerService.deleteById(id)
    }

}