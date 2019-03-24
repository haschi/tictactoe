package com.github.haschi.tictactoe.requirements.backend.testing

import cucumber.api.java.Before
import mu.KLogging
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.env.Environment
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.concurrent.TimeoutException

class Hooks(private val environment: Environment, @LocalServerPort val port: Int) {

    private val webClient = WebClient.builder()
        .baseUrl("http://localhost:$port")
        .build()


    @Before
    fun reset() {
        logger.info { "Before Hook reset()" }

        webClient.get()
            .uri("/actuator/health")
            .retrieve()
            .bodyToMono(String::class.java)
            .log()
            .blockOptional(Duration.ofMillis(500))
            .orElseThrow { TimeoutException() }
    }

    companion object : KLogging()
}