package com.example.ktschool

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val logger = mu.KotlinLogging.logger {}
@SpringBootApplication
class KtSchoolApplication

fun main(args: Array<String>) {
	runApplication<KtSchoolApplication>(*args)
}
