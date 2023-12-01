package com.example.demo300

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(TestController::class)
class TestControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun testHelloEndpoint() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().string("hello123"))
            ㄱㄴㄷㄹ
    }
}
