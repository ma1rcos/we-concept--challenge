package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import org.springframework.stereotype.Service

@Service
class TournamentService(
    private val tournamentRepository: TournamentRepositoryPort,
    private val playerRepository: PlayerRepositoryPort
) {
    fun create(tournament: Tournament): Tournament = tournamentRepository.save(tournament)
    fun findById(id: Long): Tournament? = tournamentRepository.findById(id)
    fun addPlayer(tournamentId: Long, playerId: Long): Tournament {
        val player = playerRepository.findById(playerId) 
            ?: throw IllegalArgumentException("Player not found")
        return tournamentRepository.addPlayer(tournamentId, player)
    }
}