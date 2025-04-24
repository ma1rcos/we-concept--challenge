package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepositoryPort: PlayerRepositoryPort
) {

    fun createPlayer(player: Player): Player {
        return playerRepositoryPort.save(player)
    }

}