package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.*
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class TournamentServiceTest {

    private val tournamentRepository = mockk<TournamentRepositoryPort>()
    private val playerService = mockk<PlayerService>()
    private val tournamentService = TournamentService(tournamentRepository, playerService)

    @Test
    fun `create should return saved tournament when valid`() {
        val tournament = Tournament(name = "Test", date = LocalDate.now())
        val savedTournament = tournament.copy(id = 1L)

        every { tournamentRepository.existsByName("Test") } returns false
        every { tournamentRepository.save(tournament) } returns savedTournament

        val result = tournamentService.create(tournament)

        assertEquals(1L, result.id)
        verify { tournamentRepository.save(tournament) }
    }

    @Test
    fun `create should throw BadRequestException when name is blank`() {
        val tournament = Tournament(name = " ", date = LocalDate.now())

        val exception = assertThrows<BadRequestException> {
            tournamentService.create(tournament)
        }

        assertEquals(Message.TOURNAMENT_NAME_EMPTY, exception.message)
    }

    @Test
    fun `create should throw ConflictException when name already exists`() {
        val tournament = Tournament(name = "Existing", date = LocalDate.now())

        every { tournamentRepository.existsByName("Existing") } returns true

        val exception = assertThrows<ConflictException> {
            tournamentService.create(tournament)
        }

        assertEquals(Message.TOURNAMENT_NAME_EXISTS.format("Existing"), exception.message)
    }

    @Test
    fun `addPlayer should return tournament with added player when valid`() {
        val player = Player(id = 1L, name = "Player")
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now())
        val updatedTournament = tournament.copy(players = mutableSetOf(player))

        every { tournamentRepository.findById(1L) } returns tournament
        every { playerService.getById(1L) } returns player
        every { tournamentRepository.addPlayer(1L, player) } returns updatedTournament

        val result = tournamentService.addPlayer(1L, 1L)

        assertEquals(1, result.players.size)
        assertTrue(result.players.contains(player))
    }

    @Test
    fun `addPlayer should throw NotFoundException when tournament not found`() {
        every { tournamentRepository.findById(1L) } returns null

        val exception = assertThrows<NotFoundException> {
            tournamentService.addPlayer(1L, 1L)
        }

        assertEquals(Message.TOURNAMENT_NOT_FOUND.format(1L), exception.message)
    }

    @Test
    fun `addPlayer should throw PreconditionFailedException when tournament is finished`() {
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now(), isFinished = true)

        every { tournamentRepository.findById(1L) } returns tournament

        val exception = assertThrows<PreconditionFailedException> {
            tournamentService.addPlayer(1L, 1L)
        }

        assertEquals(Message.TOURNAMENT_ALREADY_FINISHED, exception.message)
    }

    @Test
    fun `addPlayer should throw ConflictException when player already registered`() {
        val player = Player(id = 1L, name = "Player")
        val tournament = Tournament(
            id = 1L,
            name = "Tournament",
            date = LocalDate.now(),
            players = mutableSetOf(player))

        every { tournamentRepository.findById(1L) } returns tournament
        every { playerService.getById(1L) } returns player

        val exception = assertThrows<ConflictException> {
            tournamentService.addPlayer(1L, 1L)
        }

        assertEquals(Message.PLAYER_ALREADY_REGISTERED, exception.message)
    }

    @Test
    fun `removePlayer should return tournament without player when valid`() {
        val player = Player(id = 1L, name = "Player")
        val tournament = Tournament(
            id = 1L,
            name = "Tournament",
            date = LocalDate.now(),
            players = mutableSetOf(player))
        val updatedTournament = tournament.copy(players = mutableSetOf())

        every { tournamentRepository.findById(1L) } returns tournament
        every { tournamentRepository.removePlayer(1L, 1L) } returns updatedTournament

        val result = tournamentService.removePlayer(1L, 1L)

        assertTrue(result.players.isEmpty())
    }

    @Test
    fun `removePlayer should throw NotFoundException when player not in tournament`() {
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now())

        every { tournamentRepository.findById(1L) } returns tournament

        val exception = assertThrows<NotFoundException> {
            tournamentService.removePlayer(1L, 1L)
        }

        assertEquals(Message.PLAYER_NOT_IN_TOURNAMENT, exception.message)
    }

    @Test
    fun `finishTournament should return finished tournament when valid`() {
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now())
        val finishedTournament = tournament.copy(isFinished = true)

        every { tournamentRepository.findById(1L) } returns tournament
        every { tournamentRepository.save(finishedTournament) } returns finishedTournament

        val result = tournamentService.finishTournament(1L)

        assertTrue(result.isFinished)
    }

    @Test
    fun `finishTournament should throw PreconditionFailedException when already finished`() {
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now(), isFinished = true)

        every { tournamentRepository.findById(1L) } returns tournament

        val exception = assertThrows<PreconditionFailedException> {
            tournamentService.finishTournament(1L)
        }

        assertEquals(Message.TOURNAMENT_ALREADY_FINISHED, exception.message)
    }

    @Test
    fun `getById should return tournament when found`() {
        val tournament = Tournament(id = 1L, name = "Tournament", date = LocalDate.now())

        every { tournamentRepository.findById(1L) } returns tournament

        val result = tournamentService.getById(1L)

        assertEquals(tournament, result)
    }

    @Test
    fun `listPlayers should return players from tournament`() {
        val player = Player(id = 1L, name = "Player")
        val tournament = Tournament(
            id = 1L,
            name = "Tournament",
            date = LocalDate.now(),
            players = mutableSetOf(player))

        every { tournamentRepository.findById(1L) } returns tournament

        val result = tournamentService.listPlayers(1L)

        assertEquals(1, result.size)
        assertEquals(player, result.first())
    }

}