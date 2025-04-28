package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.RankingService
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.RankingResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.RankingMapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ranking")
class RankingController(
    private val rankingService: RankingService
) {

    @GetMapping("/global")
    fun getGlobalRankings(): List<RankingResponse> = rankingService.getGlobalRankings().map { RankingMapper.toResponse(it) }

    @GetMapping("/tournament/{tournamentId}")
    fun getTournamentRankings(@PathVariable tournamentId: Long): List<RankingResponse> = rankingService.getTournamentRankings(tournamentId).map { RankingMapper.toResponse(it) }


    @GetMapping("/player/{playerId}")
    fun getPlayerRanking(@PathVariable playerId: Long): PlayerRankingSummaryResponse {
        val globalRanking = rankingService.getGlobalRankingForPlayer(playerId)
        val tournamentRankings = rankingService.getTournamentRankingsForPlayer(playerId)
        return PlayerRankingSummaryResponse(
            playerId = playerId,
            globalScore = globalRanking?.totalScore ?: 0,
            tournamentScores = tournamentRankings.associate { it.tournamentId!! to it.totalScore }
        )
    }

}