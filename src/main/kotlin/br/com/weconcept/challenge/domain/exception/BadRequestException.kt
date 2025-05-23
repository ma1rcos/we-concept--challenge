package br.com.weconcept.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String) :
    ApiException(HttpStatus.BAD_REQUEST, message)