package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Player

interface PlayerRepositoryPort {

    fun save(player: Player): Player

}