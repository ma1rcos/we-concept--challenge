package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Player

interface PlayerRepositoryPort {
    fun save(player: Player): Player
    fun findById(id: Long): Player?
    fun findByName(name: String): Player?
    fun existsById(id: Long): Boolean
    fun update(player: Player): Player
    fun deleteById(id: Long)
}