package com.example.demo300

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
@RestController
class TestController {

    @Autowired
    private lateinit var mockMvc: MockMvc;

    @Test
    fun helloTest(){
        mockMvc.perform(get("/"))
            .andExpect(status().isOk);
    }
}