package com.example.demo300

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TestController {
    @GetMapping("/")
    fun hello(): String {
        return "hello123"
    }
}