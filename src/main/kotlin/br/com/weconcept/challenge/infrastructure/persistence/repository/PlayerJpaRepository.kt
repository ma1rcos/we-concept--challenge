package br.com.weconcept.challenge.infrastructure.persistence.repository

import br.com.weconcept.challenge.domain.model.Player
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerJpaRepository : JpaRepository<Player, Long> {
    fun findByName(name: String): Player?
}