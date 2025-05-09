package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.persistence.repository.TournamentJpaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
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

    @Test
    fun `should find tournament by id`() {
        val tournament = Tournament(id = 1L, name = "Test Tournament", date = LocalDate.now())
        every { tournamentJpaRepository.findById(1L) } returns Optional.of(tournament)
        val result = tournamentRepositoryAdapter.findById(1L)
        assertEquals(1L, result?.id)
        assertEquals("Test Tournament", result?.name)
    }

    @Test
    fun `should return null when tournament not found`() {
        every { tournamentJpaRepository.findById(1L) } returns Optional.empty()
        val result = tournamentRepositoryAdapter.findById(1L)
        assertNull(result)
    }

    @Test
    fun `should add player to tournament successfully`() {
        val player = Player(id = 1L, name = "Test Player")
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now(),
            players = mutableSetOf()
        )
        val updatedTournament = tournament.copy(players = mutableSetOf(player))
        every { tournamentJpaRepository.findById(1L) } returns Optional.of(tournament)
        every { tournamentJpaRepository.save(any()) } returns updatedTournament
        val result = tournamentRepositoryAdapter.addPlayer(1L, player)
        assertEquals(1, result.players.size)
        assertTrue(result.players.contains(player))
        verify {
            tournamentJpaRepository.findById(1L)
            tournamentJpaRepository.save(any())
        }
    }

    @Test
    fun `should remove player from tournament successfully`() {
        val player = Player(id = 1L, name = "Test Player")
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now(),
            players = mutableSetOf(player)
        )
        val updatedTournament = tournament.copy(players = mutableSetOf())
        every { tournamentJpaRepository.findById(1L) } returns Optional.of(tournament)
        every { tournamentJpaRepository.save(any()) } returns updatedTournament
        val result = tournamentRepositoryAdapter.removePlayer(1L, 1L)
        assertEquals(0, result.players.size)
        verify {
            tournamentJpaRepository.findById(1L)
            tournamentJpaRepository.save(any())
        }
    }

    @Test
    fun `should finish tournament successfully`() {
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now()
        )
        val finishedTournament = tournament.copy(isFinished = true)
        every { tournamentJpaRepository.findById(1L) } returns Optional.of(tournament)
        every { tournamentJpaRepository.save(any()) } returns finishedTournament
        val result = tournamentRepositoryAdapter.finishTournament(1L)
        assertTrue(result.isFinished)
        verify {
            tournamentJpaRepository.findById(1L)
            tournamentJpaRepository.save(any())
        }
    }

}