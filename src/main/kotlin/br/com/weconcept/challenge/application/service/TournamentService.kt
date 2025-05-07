package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.TournamentRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.ConflictException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.exception.PreconditionFailedException
import br.com.weconcept.challenge.domain.model.Player
import br.com.weconcept.challenge.domain.model.Tournament
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TournamentService(
    private val repository: TournamentRepositoryPort,
    private val playerService: PlayerService
) {

    @Transactional
    fun create(tournament: Tournament): Tournament {
        validateTournament(tournament)
        if (repository.existsByName(tournament.name)) {
            throw ConflictException(Message.TOURNAMENT_NAME_EXISTS.format(tournament.name))
        }
        return repository.save(tournament)
    }

    @Transactional
    fun addPlayer(tournamentId: Long, playerId: Long): Tournament {
        val tournament = repository.findById(tournamentId)
            ?: throw NotFoundException(Message.TOURNAMENT_NOT_FOUND.format(tournamentId))

        checkTournamentActive(tournament)
        val player = playerService.getById(playerId)

        if (tournament.players.any { it.id == playerId }) {
            throw ConflictException(Message.PLAYER_ALREADY_REGISTERED)
        }

        return repository.addPlayer(tournamentId, player)
    }

    @Transactional
    fun finishTournament(tournamentId: Long): Tournament {
        val tournament = repository.findById(tournamentId)
            ?: throw NotFoundException(Message.TOURNAMENT_NOT_FOUND.format(tournamentId))

        checkTournamentActive(tournament)
        tournament.isFinished = true
        return repository.save(tournament)
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): Tournament {
        return repository.findById(id)
            ?: throw NotFoundException(Message.TOURNAMENT_NOT_FOUND.format(id))
    }

    @Transactional(readOnly = true)
    fun listPlayers(tournamentId: Long): Set<Player> {
        return getById(tournamentId).players
    }

    @Transactional
    fun removePlayer(tournamentId: Long, playerId: Long): Tournament {
        val tournament = repository.findById(tournamentId)
            ?: throw NotFoundException(Message.TOURNAMENT_NOT_FOUND.format(tournamentId))

        checkTournamentActive(tournament)

        if (!tournament.players.any { it.id == playerId }) {
            throw NotFoundException(Message.PLAYER_NOT_IN_TOURNAMENT)
        }
        return repository.removePlayer(tournamentId, playerId)
    }

    private fun validateTournament(tournament: Tournament) {
        if (tournament.name.isBlank()) {
            throw BadRequestException(Message.TOURNAMENT_NAME_EMPTY)
        }
    }

    private fun checkTournamentActive(tournament: Tournament) {
        if (tournament.isFinished) {
            throw PreconditionFailedException(Message.TOURNAMENT_ALREADY_FINISHED)
        }
    }

}