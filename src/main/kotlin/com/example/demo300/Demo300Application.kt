package com.example.demo300

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class Demo300Application

fun main(args: Array<String>) {
	runApplication<Demo300Application>(*args)
}
