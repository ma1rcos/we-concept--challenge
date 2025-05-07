package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament

interface TournamentRepositoryPort {
    fun save(tournament: Tournament): Tournament
    fun findById(id: Long): Tournament?
    fun existsByName(name: String): Boolean
    fun addPlayer(tournamentId: Long, player: Player): Tournament
    fun removePlayer(tournamentId: Long, playerId: Long): Tournament
    fun finishTournament(tournamentId: Long): Tournament
}