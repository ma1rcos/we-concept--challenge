package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.persistence.repository.TournamentJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.util.*

class TournamentRepositoryAdapterTest {

    private val tournamentJpaRepository = mockk<TournamentJpaRepository>()
    private val tournamentRepositoryAdapter = TournamentRepositoryAdapter(tournamentJpaRepository)

    @Test
    fun `should save tournament successfully`() {
        val tournament = Tournament(name = "Test Tournament", date = LocalDate.now())
        val savedTournament = tournament.copy(id = 1L)
        every { tournamentJpaRepository.save(tournament) } returns savedTournament
        val result = tournamentRepositoryAdapter.save(tournament)
        assertEquals(1L, result.id)
        verify { tournamentJpaRepository.save(tournament) }
    }

}