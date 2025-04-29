package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepositoryPort: PlayerRepositoryPort
) {

    fun create(player: Player): Player {
        if (playerRepositoryPort.findByName(player.name) != null) {
            throw IllegalArgumentException("Já existe um jogador com este nome")
        }
        return playerRepositoryPort.save(player)
    }

    fun update(player: Player): Player {
        val existingPlayerWithSameName = playerRepositoryPort.findByName(player.name)
        if (existingPlayerWithSameName != null && existingPlayerWithSameName.id != player.id) {
            throw IllegalArgumentException("Já existe um jogador com este nome")
        }
        return playerRepositoryPort.update(player)
    }

    fun getById(id: Long): Player? = playerRepositoryPort.findById(id)

    fun getByName(name: String): Player? = playerRepositoryPort.findByName(name)

    fun deleteById(id: Long) = playerRepositoryPort.deleteById(id)

}