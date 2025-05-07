package br.com.weconcept.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus
abstract class ApiException(
    val status: HttpStatus,
    message: String
) : RuntimeException(message) {
    init {
        fillInStackTrace()
    }
}