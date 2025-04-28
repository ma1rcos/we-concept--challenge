package br.com.weconcept.challenge.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class ChallengeExecution(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "player_id")
    val playerId: Long,
    @Column(name = "challenge_id")
    val challengeId: Long,
    val success: Boolean,
    val score: Int,
    val result: String,
    @Column(name = "tournament_id")
    val tournamentId: Long? = null,
    val executedAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        0,
        0,
        0,
        false,
        0,
        "",
        null,
        LocalDateTime.now()
    )
}