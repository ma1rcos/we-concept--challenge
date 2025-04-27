package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.persistence.repository.TournamentJpaRepository
import org.springframework.stereotype.Component

@Component
class TournamentRepositoryAdapter(
    private val tournamentJpaRepository: TournamentJpaRepository
) : TournamentRepositoryPort {
    override fun save(tournament: Tournament): Tournament = tournamentJpaRepository.save(tournament)
    override fun findById(id: Long): Tournament? = tournamentJpaRepository.findById(id).orElse(null)
    override fun addPlayer(tournamentId: Long, player: Player): Tournament {
        val tournament = tournamentJpaRepository.findById(tournamentId)
            .orElseThrow { IllegalArgumentException("Tournament not found") }
        if (tournament.isFinished) {
            throw IllegalStateException("Tournament already finished")
        }
        tournament.players.add(player)
        return tournamentJpaRepository.save(tournament)
    }
    override fun removePlayer(tournamentId: Long, playerId: Long): Tournament {
        val tournament = tournamentJpaRepository.findById(tournamentId)
            .orElseThrow { IllegalArgumentException("Tournament not found") }
        if (tournament.isFinished) {
            throw IllegalStateException("Tournament already finished")
        }
        val player = tournament.players.find { it.id == playerId }
            ?: throw IllegalArgumentException("Player not found in tournament")
        tournament.players.remove(player)
        return tournamentJpaRepository.save(tournament)
    }
    override fun finishTournament(tournamentId: Long): Tournament {
        val tournament = tournamentJpaRepository.findById(tournamentId)
            .orElseThrow { IllegalArgumentException("Tournament not found") }
        
        if (tournament.isFinished) {
            throw IllegalStateException("Tournament already finished")
        }
        
        tournament.isFinished = true
        return tournamentJpaRepository.save(tournament)
    }
}