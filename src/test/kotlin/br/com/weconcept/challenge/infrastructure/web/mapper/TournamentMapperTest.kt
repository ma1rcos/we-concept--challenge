package br.com.weconcept.challenge.infrastructure.web.mapper

import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import br.com.weconcept.challenge.infrastructure.web.dto.request.CreateTournamentRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TournamentMapperTest {

    private val mapper = TournamentMapper()

    @Test
    fun `toDomain should map CreateTournamentRequest to Tournament`() {
        val request = CreateTournamentRequest(
            name = "Test Tournament",
            date = LocalDate.now()
        )

        val tournament = mapper.toDomain(request)

        assertEquals("Test Tournament", tournament.name)
        assertEquals(LocalDate.now(), tournament.date)
        assertFalse(tournament.isFinished)
    }

    @Test
    fun `toResponse should map Tournament to TournamentResponse`() {
        val now = LocalDateTime.now()
        val player = Player(
            id = 1L,
            name = "Test Player",
            createdAt = now,
            updatedAt = now
        )
        val tournament = Tournament(
            id = 1L,
            name = "Test Tournament",
            date = LocalDate.now(),
            isFinished = false,
            createdAt = now,
            updatedAt = now,
            players = mutableSetOf(player)
        )

        val response = mapper.toResponse(tournament)

        assertEquals(1L, response.id)
        assertEquals("Test Tournament", response.name)
        assertEquals(LocalDate.now(), response.date)
        assertFalse(response.isFinished)
        assertEquals(1, response.players.size)
        assertEquals("Test Player", response.players[0].name)
    }

}