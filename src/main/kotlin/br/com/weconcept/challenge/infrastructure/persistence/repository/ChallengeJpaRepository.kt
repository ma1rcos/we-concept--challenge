package br.com.weconcept.challenge.infrastructure.persistence.repository

import br.com.weconcept.challenge.domain.model.Challenge
import org.springframework.data.jpa.repository.JpaRepository

interface ChallengeJpaRepository : JpaRepository<Challenge, Long>