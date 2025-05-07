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

    override fun save(tournament: Tournament): Tournament =
        tournamentJpaRepository.save(tournament)

    override fun findById(id: Long): Tournament? =
        tournamentJpaRepository.findById(id).orElse(null)

    override fun existsByName(name: String): Boolean =
        tournamentJpaRepository.existsByName(name)

    override fun addPlayer(tournamentId: Long, player: Player): Tournament =
        tournamentJpaRepository.findById(tournamentId).get().apply { players.add(player) }.let { tournamentJpaRepository.save(it) }

    override fun removePlayer(tournamentId: Long, playerId: Long): Tournament =
        tournamentJpaRepository.findById(tournamentId).get().apply {
            players.removeIf { it.id == playerId }
        }.let { tournamentJpaRepository.save(it) }

    override fun finishTournament(tournamentId: Long): Tournament =
        tournamentJpaRepository.findById(tournamentId).get().apply {
            isFinished = true
        }.let { tournamentJpaRepository.save(it) }

}