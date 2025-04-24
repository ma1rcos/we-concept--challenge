package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.PlayerService
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreatePlayerRequest
import br.com.weconcept.challenge.infrastructure.web.dto.response.CreatePlayerResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.PlayerMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/player")
class PlayerController(
    private val playerService: PlayerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPlayer(@RequestBody request: CreatePlayerRequest): CreatePlayerResponse {
        val player = PlayerMapper.toDomain(request)
        val createdPlayer = playerService.createPlayer(player)
        return PlayerMapper.toResponse(createdPlayer)
    }

}