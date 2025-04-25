package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.CreatePlayerResponse
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
    fun create(@RequestBody request: CreatePlayerRequest): CreatePlayerResponse {
        val player = PlayerMapper.toDomain(request)
        val createdPlayer = playerService.create(player)
        return PlayerMapper.toResponse(createdPlayer)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CreatePlayerResponse {
        return playerService.getById(id)?.let { PlayerMapper.toResponse(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun getByName(@RequestParam name: String): CreatePlayerResponse {
        return playerService.getByName(name)?.let { PlayerMapper.toResponse(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}