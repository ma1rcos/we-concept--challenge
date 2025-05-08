package br.com.weconcept.challenge.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Challenge(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String,
    val weight: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    constructor() : this(
        0,
        "",
        0,
        LocalDateTime.now(),
        LocalDateTime.now()
    )
    companion object {
        val FIBONACCI = Challenge(name = "Fibonacci", weight = 10)
        val PALINDROME = Challenge(name = "Palindrome", weight = 5)
        val SORTING = Challenge(name = "Sorting", weight = 8)
    }
}