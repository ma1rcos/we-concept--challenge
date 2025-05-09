package br.com.weconcept.challenge.infrastructure.web.controller

import br.com.weconcept.challenge.application.service.AppService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class AppControllerTest {

    private val appService = mockk<AppService>()
    private val controller = AppController(appService)
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `getHello should return hello message`() {
        every { appService.getHello() } returns "Hello, world!"

        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello, world!"))
    }

}