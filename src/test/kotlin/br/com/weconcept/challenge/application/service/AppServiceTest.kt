package br.com.weconcept.challenge.application.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppServiceTest {

    private val appService = AppService()

    @Test
    fun `getHello should return hello message`() {
        val result = appService.getHello()
        assertEquals("Hello, world!", result)
    }

}