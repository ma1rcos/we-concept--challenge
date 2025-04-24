package br.com.weconcept.challenge.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Player(
    @Column(unique = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true)
    val name: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
){
    constructor() : this(0, "", LocalDateTime.now(), LocalDateTime.now())
}
