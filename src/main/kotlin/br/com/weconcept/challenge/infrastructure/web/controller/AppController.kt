package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.AppService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AppController(
    private val appService: AppService
) {

    @Operation(
        summary = "Get Hello",
        description = "Returns a 'hello, world!'"
    )
    @GetMapping
    fun getHello(): String = this.appService.getHello()

}