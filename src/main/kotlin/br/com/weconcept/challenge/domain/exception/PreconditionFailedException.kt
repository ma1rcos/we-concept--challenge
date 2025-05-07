package br.com.weconcept.challenge.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
class PreconditionFailedException(message: String) :
    ApiException(HttpStatus.PRECONDITION_FAILED, message)