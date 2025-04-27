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
}