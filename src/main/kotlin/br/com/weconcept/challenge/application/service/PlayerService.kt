package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepositoryPort: PlayerRepositoryPort
) {

    fun create(player: Player): Player = playerRepositoryPort.save(player)

    fun getById(id: Long): Player? = playerRepositoryPort.findById(id)

    fun getByName(name: String): Player? = playerRepositoryPort.findByName(name)

    fun update(player: Player): Player = playerRepositoryPort.update(player)

    fun deleteById(id: Long) = playerRepositoryPort.deleteById(id)

}