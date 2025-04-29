package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.RankingService
import br.com.weconcept.challenge.infrastructure.web.dto.response.PlayerRankingSummaryResponse
import br.com.weconcept.challenge.infrastructure.web.dto.response.RankingResponse
import br.com.weconcept.challenge.infrastructure.web.mapper.RankingMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Ranking", description = "Operations related to player rankings and scores")
@RestController
@RequestMapping("/ranking")
class RankingController(
    private val rankingService: RankingService
) {

    @Operation(
        summary = "Retrieve global rankings",
        description = "Fetches all players ranked by their total scores in descending order."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Global rankings successfully retrieved.",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = RankingResponse::class)),
                examples = [ExampleObject(value = """
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
                """)]
            )]
        ),
        ApiResponse(responseCode = "500", description = "Unexpected error occurred.")
    ])
    @GetMapping("/global")
    fun getGlobalRankings(): List<RankingResponse> = rankingService.getGlobalRankings().map { RankingMapper.toResponse(it) }

    @Operation(
        summary = "Retrieve tournament rankings",
        description = "Fetches rankings of players for a specific tournament, ordered by their scores in descending order."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Tournament rankings successfully retrieved.",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = RankingResponse::class)),
                examples = [ExampleObject(value = """
                [
                    {
                        "playerId": 3,
                        "totalScore": 200,
                        "tournamentId": 1
                    },
                    {
                        "playerId": 1,
                        "totalScore": 180,
                        "tournamentId": 1
                    }
                ]
                """)]
            )]
        ),
        ApiResponse(responseCode = "404", description = "Tournament not found.")
    ])
    @GetMapping("/tournament/{tournamentId}")
    fun getTournamentRankings(
        @Parameter(
            description = "ID of the tournament whose rankings should be retrieved.",
            example = "1",
            required = true
        )
        @PathVariable tournamentId: Long
    ): List<RankingResponse> = rankingService.getTournamentRankings(tournamentId).map { RankingMapper.toResponse(it) }

    @Operation(
        summary = "Retrieve player's ranking summary",
        description = "Fetches the player's global score along with their scores in individual tournaments."
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Player ranking data successfully retrieved.",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PlayerRankingSummaryResponse::class),
                examples = [ExampleObject(value = """
                {
                    "playerId": 1,
                    "globalScore": 330,
                    "tournamentScores": {
                        "1": 180,
                        "2": 150
                    }
                }
                """)]
            )]
        ),
        ApiResponse(responseCode = "404", description = "Player not found.")
    ])
    @GetMapping("/player/{playerId}")
    fun getPlayerRanking(
        @Parameter(
            description = "ID of the player whose ranking data should be retrieved.",
            example = "1",
            required = true
        )
        @PathVariable playerId: Long
    ): PlayerRankingSummaryResponse {
        val globalRanking = rankingService.getGlobalRankingForPlayer(playerId)
        val tournamentRankings = rankingService.getTournamentRankingsForPlayer(playerId)
        return PlayerRankingSummaryResponse(
            playerId = playerId,
            globalScore = globalRanking?.totalScore ?: 0,
            tournamentScores = tournamentRankings.associate { it.tournamentId!! to it.totalScore }
        )
    }
}
