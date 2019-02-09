package com.github.haschi.tictactoe.requirements.backend.testing

import cucumber.api.java.Before
import mu.KLogging
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.env.Environment
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

class Hooks(private val environment: Environment, @LocalServerPort val port: Int) {

    val webClient = WebClient.builder()
        .baseUrl("http://localhost:$port")
        .filter(logRequest())
        .build()


    @Before
    fun reset() {
        logger.info { "Before Hook reset()" }
        val response = webClient.get()
            .uri("/actuator/health")
            .retrieve()
            .bodyToFlux(String::class.java)
            .blockFirst()

        logger.info { "Rückgabe ${response}" }
    }

    companion object : KLogging() {
        fun logRequest(): ExchangeFilterFunction {
            return ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
                logger.info { "Request: ${clientRequest.method()} ${clientRequest.url()}" }
                clientRequest.headers().forEach {
                    logger.info { "${it.key}: ${it.value}" }
                }
                val body = clientRequest.body().toMono().block()
                logger.info { "Body: $body" }
                Mono.just(clientRequest)
            }
        }
    }
}