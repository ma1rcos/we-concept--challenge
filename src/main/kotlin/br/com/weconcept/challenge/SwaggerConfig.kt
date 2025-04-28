package br.com.weconcept.challenge

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("WeConcept Challenge API")
                    .version("1.0")
                    .description("API for managing challenges, tournaments and rankings")
            )
            .servers(listOf(
                Server().url("http://localhost:8080/api").description("Local server")
            ))
    }
}