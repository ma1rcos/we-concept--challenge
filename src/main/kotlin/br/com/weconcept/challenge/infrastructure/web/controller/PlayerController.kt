package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.request.UpdatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import io.swagger.v3.oas.annotations.Operation
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
        description = "Registers a new player in the system"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Player created successfully",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PlayerResponse::class),
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
        ApiResponse(responseCode = "400", description = "Invalid input data")
    ])
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: CreatePlayerRequest): PlayerResponse {
        val player = PlayerMapper.toDomain(request)
        val createdPlayer = playerService.create(player)
        return PlayerMapper.toResponse(createdPlayer)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): PlayerResponse {
        return playerService.getById(id)?.let { PlayerMapper.toResponse(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun getByName(@RequestParam name: String): PlayerResponse {
        return playerService.getByName(name)?.let { PlayerMapper.toResponse(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UpdatePlayerRequest): PlayerResponse {
        val existingPlayer = playerService.getById(id) ?: throw IllegalArgumentException("Player not found")
        val updatedPlayer = existingPlayer.copy(name = request.name)
        val result = playerService.update(updatedPlayer)
        return PlayerMapper.toResponse(result)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Long) {
        playerService.deleteById(id)
    }

}