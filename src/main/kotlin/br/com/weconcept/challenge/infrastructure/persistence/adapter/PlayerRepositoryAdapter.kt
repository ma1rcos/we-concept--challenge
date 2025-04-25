package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.infrastructure.persistence.repository.PlayerJpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PlayerRepositoryAdapter(
    private val playerJpaRepository: PlayerJpaRepository
) : PlayerRepositoryPort {
    override fun save(player: Player): Player = playerJpaRepository.save(player)
    override fun findById(id: Long): Player? = playerJpaRepository.findById(id).orElse(null)
    override fun findByName(name: String): Player? = playerJpaRepository.findByName(name)
    override fun deleteById(id: Long) = playerJpaRepository.deleteById(id)
    override fun update(player: Player): Player {
        val existingPlayer = playerJpaRepository.findById(player.id)
            .orElseThrow { IllegalArgumentException("Player not found with id: ${player.id}") }
        return playerJpaRepository.save(
            existingPlayer.copy(
                name = player.name,
                updatedAt = LocalDateTime.now()
            )
        )
    }
}