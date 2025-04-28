package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.AppService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class AppController(
    private val appService: AppService
) {
    @GetMapping
    fun getHello(): String = this.appService.getHello()
}