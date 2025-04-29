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
        summary = "Retrieve a friendly greeting",
        description = "Returns a simple 'hello, world!' message to confirm the application is running."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Greeting returned successfully",
                content = [io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "text/plain",
                    examples = [io.swagger.v3.oas.annotations.media.ExampleObject(
                        value = "hello, world!"
                    )]
                )]
            )
        ]
    )
    @GetMapping
    fun getHello(): String = this.appService.getHello()

}