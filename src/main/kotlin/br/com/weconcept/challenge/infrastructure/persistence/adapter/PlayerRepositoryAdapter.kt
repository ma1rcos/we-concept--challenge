package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.persistence.repository.PlayerJpaRepository
import org.springframework.stereotype.Component

@Component
class PlayerRepositoryAdapter(
    private val playerJpaRepository: PlayerJpaRepository
) : PlayerRepositoryPort {

    override fun save(player: Player): Player = playerJpaRepository.save(player)

}