package br.com.weconcept.challenge.infrastructure.persistence.adapter

import br.com.weconcept.challenge.domain.model.Ranking
import br.com.weconcept.challenge.infrastructure.persistence.repository.RankingJpaRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RankingRepositoryAdapterTest {

    private val rankingJpaRepository = mockk<RankingJpaRepository>()
    private val adapter = RankingRepositoryAdapter(rankingJpaRepository)

    @Test
    fun `findByPlayerAndTournamentIsNull should return ranking`() {
        val ranking = Ranking(playerId = 1L, totalScore = 100)
        every { rankingJpaRepository.findByPlayerIdAndTournamentIdIsNull(1L) } returns ranking
        val result = adapter.findByPlayerAndTournamentIsNull(1L)
        assertEquals(ranking, result)
    }

    @Test
    fun `save should delegate to JpaRepository`() {
        val ranking = Ranking(playerId = 1L, totalScore = 100)
        every { rankingJpaRepository.save(ranking) } returns ranking
        val result = adapter.save(ranking)
        assertEquals(ranking, result)
    }

}