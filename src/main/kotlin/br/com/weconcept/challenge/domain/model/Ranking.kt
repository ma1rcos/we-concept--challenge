package br.com.weconcept.challenge.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Ranking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "player_id")
    val playerId: Long,
    var totalScore: Int = 0,
    @Column(name = "tournament_id")
    val tournamentId: Long? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        0,
        0,
        0,
        null,
        LocalDateTime.now(),
        LocalDateTime.now()
    )
}