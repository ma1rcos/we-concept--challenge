package br.com.weconcept.challenge.domain.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Tournament(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val date: LocalDate,
    val isFinished: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @ManyToMany
    @JoinTable(
        name = "tournament_player",
        joinColumns = [JoinColumn(name = "tournament_id")],
        inverseJoinColumns = [JoinColumn(name = "player_id")]
    )
    val players: MutableSet<Player> = mutableSetOf()
) {
    constructor() : this(0, "", LocalDate.now(), false, LocalDateTime.now(), LocalDateTime.now())
}