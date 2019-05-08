package com.github.haschi.tictactoe.backend.controller

import backend.controller.withDecoder
import com.github.haschi.tictactoe.backend.WebFluxConfiguration
import com.github.haschi.tictactoe.backend.marshalling.CodecsConfiguration
import com.github.haschi.tictactoe.domain.Anwendereigenschaften
import com.github.haschi.tictactoe.domain.WelcheEigenschaftenBesitztAnwender
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.test
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [AnwenderController::class])
@Import(value = [WebFluxConfiguration::class, CodecsConfiguration::class])
@ActiveProfiles("test")
class AnwenderControllerTest(
    @Autowired private val decoder: Jackson2JsonDecoder,
    @Autowired private val client: WebTestClient
) {

    @MockBean
    private lateinit var queryBus: QueryGateway

    @Nested
    @DisplayName("> GET /api/anwender/{id}")
    inner class GetApiAnwender {

        private fun getAnwender(id: String): FluxExchangeResult<AnwenderResource> {

            return client.withDecoder(decoder)
                .get()
                .uri("/api/anwender/$id")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .returnResult(AnwenderResource::class.java)
        }

        @Test
        @DisplayName("< 200 OK")
        fun `liefert Response mit Status 200 OK`() {

            val result = getAnwender("4711")
            assertThat(result.status).isEqualTo(HttpStatus.OK)
        }

        @ParameterizedTest
        @ValueSource(strings = ["4711", "4710"])
        @DisplayName("< [JSON Body]")
        fun `liefert Response mit Anwender Resource`(id: String) {
            whenever(
                queryBus.query(
                    WelcheEigenschaftenBesitztAnwender(Aggregatkennung(id)),
                    Anwendereigenschaften::class.java
                )
            )
                .thenReturn(CompletableFuture.supplyAsync { Anwendereigenschaften("Matthias") })

            val result = getAnwender(id)
            result.responseBody.test()
                .expectNext(AnwenderResource(Aggregatkennung(id), Anwendereigenschaften("Matthias")))
                .thenCancel()
                .verify()
        }
    }
}