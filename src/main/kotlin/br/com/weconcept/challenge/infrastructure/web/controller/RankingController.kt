package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.RankingService
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.RankingResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.RankingMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Rankings", description = "Operations related to player rankings")
@RestController
@RequestMapping("/ranking")
class RankingController(
    private val rankingService: RankingService,
    private val rankingMapper: RankingMapper
) {

    @Operation(
        summary = "Get global rankings",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Rankings retrieved",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        [
                            {
                                "playerId": 1,
                                "totalScore": 150,
                                "tournamentId": null
                            },
                            {
                                "playerId": 2,
                                "totalScore": 120,
                                "tournamentId": null
                            }
                        ]
                        """
                    )]
                )]
            )
        ]
    )
    @GetMapping("/global")
    fun getGlobalRankings(): List<RankingResponse> =
        rankingService.getGlobalRankings().map { rankingMapper.toResponse(it) }

    @Operation(
        summary = "Get tournament rankings",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Tournament rankings retrieved",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        [
                            {
                                "playerId": 1,
                                "totalScore": 80,
                                "tournamentId": 1
                            },
                            {
                                "playerId": 2,
                                "totalScore": 70,
                                "tournamentId": 1
                            }
                        ]
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Tournament not found")
        ]
    )
    @GetMapping("/tournament/{tournamentId}")
    fun getTournamentRankings(@PathVariable tournamentId: Long): List<RankingResponse> =
        rankingService.getTournamentRankings(tournamentId).map { rankingMapper.toResponse(it) }

    @Operation(
        summary = "Get player ranking summary",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Player ranking summary",
                content = [Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        value = """
                        {
                            "playerId": 1,
                            "globalScore": 330,
                            "tournamentScores": {
                                "1": 180,
                                "2": 150
                            }
                        }
                        """
                    )]
                )]
            ),
            ApiResponse(responseCode = "404", description = "Player not found")
        ]
    )
    @GetMapping("/player/{playerId}")
    fun getPlayerRanking(@PathVariable playerId: Long): PlayerRankingSummaryResponse {
        return rankingService.getPlayerRanking(playerId)
    }

}