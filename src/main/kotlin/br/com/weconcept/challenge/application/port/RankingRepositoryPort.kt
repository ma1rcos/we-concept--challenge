package br.com.weconcept.challenge.application.port

import br.com.weconcept.challenge.domain.model.Ranking

interface RankingRepositoryPort {

    fun save(ranking: Ranking): Ranking

    fun findByPlayerAndTournamentIsNull(playerId: Long): Ranking?

    fun findByPlayerAndTournament(playerId: Long, tournamentId: Long): Ranking?

    fun findAllByTournamentIsNullOrderByTotalScoreDesc(): List<Ranking>

    fun findAllByTournamentOrderByTotalScoreDesc(tournamentId: Long): List<Ranking>

    fun findAllByPlayer(playerId: Long): List<Ranking>

}