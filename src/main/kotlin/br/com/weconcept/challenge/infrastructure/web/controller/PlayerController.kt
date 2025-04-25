package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.request.UpdatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/player")
class PlayerController(
    private val playerService: PlayerService
) {

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

}