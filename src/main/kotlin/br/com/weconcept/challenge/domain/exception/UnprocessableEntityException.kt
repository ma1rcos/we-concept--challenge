package br.com.weconcept.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class UnprocessableEntityException(message: String) :
    ApiException(HttpStatus.UNPROCESSABLE_ENTITY, message)