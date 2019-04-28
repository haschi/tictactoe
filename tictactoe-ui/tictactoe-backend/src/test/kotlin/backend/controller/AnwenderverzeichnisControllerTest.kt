package com.github.haschi.tictactoe.backend.controller

import backend.controller.withDecoder
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.backend.WebFluxConfiguration
import com.github.haschi.tictactoe.backend.marshalling.CodecsConfiguration
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.assertj.core.api.Assertions
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.test

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [AnwenderverzeichnisController::class])
@Import(value = [WebFluxConfiguration::class, CodecsConfiguration::class])
@ActiveProfiles("test")
@DirtiesContext
@AutoConfigureJson
class AnwenderverzeichnisControllerTest(@Autowired private val decoder: Jackson2JsonDecoder) {

    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    private lateinit var queryBus: QueryGateway

    @MockBean
    private lateinit var anwenderverzeichnis: AnwenderverzeichnisGateway

    @Nested
    @DisplayName("> GET /api/anwenderverzeichnisse/{id}")
    inner class GetApiAnwenderverzeichnis {
        private lateinit var result: FluxExchangeResult<AnwenderverzeichnisResource>

        private val anwenderverzeichnisId = Aggregatkennung()
        @BeforeEach
        fun peform() {
            result = client.withDecoder(decoder)
                .get()
                .uri("/api/anwenderverzeichnisse/${anwenderverzeichnisId.id}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .returnResult(AnwenderverzeichnisResource::class.java)
        }

        @Test
        @DisplayName("< 200 OK")
        fun `liefert Response mit Status 200 OK`() {
            Assertions.assertThat(result.status).isEqualTo(HttpStatus.OK)
        }

        @Test
        @DisplayName("< [JSON Body]")
        fun `liefert Response mit Anwenderverzeichnis Resource`() {
            result.responseBody.test()
                .expectNext(AnwenderverzeichnisResource(anwenderverzeichnisId))
                .thenCancel()
                .verify()
        }
    }
}