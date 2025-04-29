package br.com.weconcept.challenge.infrastructure.persistence.repository

import br.com.weconcept.challenge.domain.model.Tournament
import org.springframework.data.jpa.repository.JpaRepository

interface TournamentJpaRepository : JpaRepository<Tournament, Long> {

    fun existsByName(name: String): Boolean

}