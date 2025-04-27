package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TournamentServiceTest {

    private val tournamentRepository = mockk<TournamentRepositoryPort>()
    private val playerRepository = mockk<PlayerRepositoryPort>()
    private val tournamentService = TournamentService(tournamentRepository, playerRepository)

    @Test
    fun `should create tournament`() {
        val tournament = Tournament(name = "Test Tournament", date = LocalDate.now())
        every { tournamentRepository.save(tournament) } returns tournament.copy(id = 1L)
        val result = tournamentService.create(tournament)
        assertEquals(1L, result.id)
        verify { tournamentRepository.save(tournament) }
    }

}