package br.com.weconcept.challenge.application.service

import br.com.weconcept.challenge.application.port.PlayerRepositoryPort
import br.com.weconcept.challenge.domain.constant.Message
import br.com.weconcept.challenge.domain.exception.BadRequestException
import br.com.weconcept.challenge.domain.exception.ConflictException
import br.com.weconcept.challenge.domain.exception.NotFoundException
import br.com.weconcept.challenge.domain.model.Player
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlayerService(
    private val repository: PlayerRepositoryPort
) {

    @Transactional
    fun create(player: Player): Player {
        validatePlayer(player)
        validateNameUniqueness(player.name)
        return repository.save(player)
    }

    @Transactional
    fun update(player: Player): Player {
        validatePlayer(player)
        requireExists(player.id)
        validateNameUniqueness(player.name, player.id)
        return repository.update(player)
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): Player {
        return repository.findById(id)
            ?: throw NotFoundException(Message.PLAYER_NOT_FOUND_ID.format(id))
    }

    @Transactional(readOnly = true)
    fun getByName(name: String): Player {
        return repository.findByName(name)
            ?: throw NotFoundException(Message.PLAYER_NOT_FOUND_NAME.format(name))
    }

    @Transactional
    fun deleteById(id: Long) {
        requireExists(id)
        repository.deleteById(id)
    }

    private fun validatePlayer(player: Player) {
        if (player.name.isBlank()) {
            throw BadRequestException(Message.PLAYER_NAME_EMPTY)
        }
    }

    private fun requireExists(id: Long) {
        if (!repository.existsById(id)) {
            throw NotFoundException(Message.PLAYER_NOT_FOUND_ID.format(id))
        }
    }

    private fun validateNameUniqueness(name: String, excludeId: Long? = null) {
        repository.findByName(name)?.let {
            if (excludeId == null || it.id != excludeId) {
                throw ConflictException(Message.PLAYER_NAME_EXISTS.format(name))
            }
        }
    }

}