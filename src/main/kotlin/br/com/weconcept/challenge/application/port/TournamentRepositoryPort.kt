package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.domain.model.Player

interface TournamentRepositoryPort {
    fun save(tournament: Tournament): Tournament
    fun findById(id: Long): Tournament?
}