package br.com.weconcept.challenge.domain.constant

object Message {

    // Generic messages
    const val DUPLICATE_ENTITY = "Another %s with this %s already exists: %s"
    const val ENTITY_NOT_FOUND = "%s not found with %s: %s"
    const val INVALID_INPUT = "Invalid input for %s: %s"
    const val OPERATION_NOT_ALLOWED = "Operation not allowed: %s"
    const val INPUT_TOO_LONG = "Input exceeds maximum length of %d characters"
    const val INPUT_TOO_LARGE = "Input exceeds maximum size of %d elements"

    // Player messages
    const val PLAYER_NAME_EXISTS = "Player name already exists: %s"
    const val PLAYER_NOT_FOUND_ID = "Player not found with ID: %d"
    const val PLAYER_NOT_FOUND_NAME = "Player not found with name: %s"
    const val PLAYER_NAME_EMPTY = "The Player name is empty"

    // Tournament messages
    const val TOURNAMENT_NAME_EXISTS = "Tournament name already exists: %s"
    const val TOURNAMENT_NOT_FOUND = "Tournament not found with ID: %d"
    const val TOURNAMENT_ALREADY_FINISHED = "Tournament is already finished"
    const val PLAYER_ALREADY_REGISTERED = "Player already registered in tournament"
    const val PLAYER_NOT_IN_TOURNAMENT = "Player not registered in tournament"
    const val TOURNAMENT_NAME_EMPTY = "Tournament name is empty"

    // Challenge messages
    const val FIBONACCI_NEGATIVE = "Fibonacci index cannot be negative"
    const val FIBONACCI_TOO_LARGE = "Fibonacci index exceeds maximum value of %d"
    const val PALINDROME_EMPTY = "Input string cannot be empty"
    const val SORTING_EMPTY = "Numbers list cannot be empty"
    const val CHALLENGE_SUCCESS = "Challenge completed successfully"
    const val CHALLENGE_FAILURE = "Challenge failed"

    // Ranking messages
    const val INVALID_SCORE_VALUE = "Invalid score value must be greater than zero"

    // Success messages
    const val CREATED_SUCCESS = "%s created successfully"
    const val UPDATED_SUCCESS = "%s updated successfully"
    const val DELETED_SUCCESS = "%s deleted successfully"

}