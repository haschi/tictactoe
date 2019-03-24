package com.github.haschi.tictactoe.backend.controller

import backend.controller.withDecoder
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.backend.WebFluxConfiguration
import com.github.haschi.tictactoe.backend.marshalling.CodecsConfiguration
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.SubscriptionQueryResult
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
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.publisher.TestPublisher
import reactor.test.test
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [AnwenderverzeichnisCollectionController::class])
@Import(value = [WebFluxConfiguration::class, CodecsConfiguration::class])
@ActiveProfiles("test")
@DirtiesContext
@AutoConfigureJson
class AnwenderverzeichnisControllerTest(
    @Autowired private val decoder: Jackson2JsonDecoder
) {
    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    private lateinit var anwenderverzeichnis: AnwenderverzeichnisGateway

    @MockBean
    private lateinit var queryBus: QueryGateway

    @MockBean
    private lateinit var identität: Identitätsgenerator



    @Nested
    @DisplayName("> POST /api/anwenderverzeichnisse")
    inner class PostApiAnwenderverzeichnis {

        private val anwenderverzeichnisId = Aggregatkennung()

        private lateinit var result: WebTestClient.ResponseSpec

        @BeforeEach
        fun perform() {

            whenever(identität.herstellen())
                .thenReturn(anwenderverzeichnisId)

            val legeAnwenderverzeichnisAn = LegeAnwenderverzeichnisAn(anwenderverzeichnisId)

            whenever(anwenderverzeichnis.send(legeAnwenderverzeichnisAn))
                .thenReturn(CompletableFuture.supplyAsync { anwenderverzeichnisId })

            result = client
                .withDecoder(decoder)
                .post()
                .uri("/api/anwenderverzeichnisse")
                .accept(MediaType.ALL)
                .exchange()
        }

        @Test
        @DisplayName("< 201 Created")
        fun `liefert Status 201 Created`() {
            result.expectStatus().isCreated
        }

        @Test
        @DisplayName("< Location: /api/anwenderverzeichnisse/{id}")
        fun `liefert Location des neuen Anwenderverzeichnisses`() {
            result.expectHeader()
                .valueEquals(HttpHeaders.LOCATION, "/api/anwenderverzeichnisse/$anwenderverzeichnisId")
        }

        @Test
        @DisplayName("[Empty Body]")
        fun `liefert keinen Body`() {
            result.expectBody().isEmpty
        }
    }

    @Nested
    @DisplayName("> GET /api/anwenderverzeichnis")
    inner class GetApiAnwenderverzeichnis {

        private val anwenderverzeichnisId = Aggregatkennung()
        private val anwenderverzeichnis2Id = Aggregatkennung()

        @Nested
        @DisplayName("> Accept: application/stream+json")
        inner class AcceptApplicationJsonStream {
            private lateinit var result: FluxExchangeResult<AnwenderverzeichnisCollection>

            private val initialResult = AnwenderverzeichnisÜbersicht(anwenderverzeichnisId)
            private val updates = listOf(AnwenderverzeichnisÜbersicht(anwenderverzeichnisId, anwenderverzeichnis2Id))

            private val initialResultPublisher = TestPublisher.createCold<AnwenderverzeichnisÜbersicht>()
                .next(initialResult)
                .complete()

            private val updatesPublisher = TestPublisher.createCold<AnwenderverzeichnisÜbersicht>()
                .next(updates.first())
                .complete()

            @BeforeEach
            fun perform() {
                val queryResult: SubscriptionQueryResult<AnwenderverzeichnisÜbersicht, AnwenderverzeichnisÜbersicht> =
                    mock {
                        on { initialResult() } doReturn initialResultPublisher.mono()
                        on { updates() } doReturn updatesPublisher.flux()
                    }

                whenever(
                    queryBus.subscriptionQuery(
                        WelcheAnwenderverzeichnisseGibtEs,
                        AnwenderverzeichnisÜbersicht::class.java,
                        AnwenderverzeichnisÜbersicht::class.java
                    )
                )
                    .thenReturn(queryResult)

                result = client.withDecoder(decoder)
                    .get()
                    .uri("/api/anwenderverzeichnisse")
                    .accept(MediaType.APPLICATION_STREAM_JSON)
                    .exchange()
                    .returnResult(AnwenderverzeichnisCollection::class.java)
            }

            @Test
            @DisplayName("< 200 OK")
            fun `liefert Response mit Status 200 OK`() {
                assertThat(result.status).isEqualTo(HttpStatus.OK)
            }

            @Test
            @DisplayName("< [Body: AnwenderverzeichnisCollection Stream]")
            fun `liefert einen Stream von AnwendungsverzeichnisCollections`() {

                val übersichten = result.responseBody

                val initialCollection = AnwenderverzeichnisCollection(
                    initialResult.verzeichnisse.map { AnwenderverzeichnisResource(it) })

                // TODO
//                val updateCollection =
//                    AnwenderverzeichnisCollection(updates[0].verzeichnisse.map { AnwenderverzeichnisResource(it) })

                übersichten.test()
                    .expectNext(initialCollection)
                    .thenCancel()
                    .verify()
            }

            @Test
            fun `Schließt die Query Subscription durch Backpressure`() {
                result.responseBody.test(1)
                    // .expectNext(AnwenderverzeichnisCollection(initialResult.verzeichnisse.map { AnwenderverzeichnisResource(it) }))
                    .expectNextCount(1)
                    .thenCancel()
                    .verifyThenAssertThat()
                    .hasNotDroppedElements()

                initialResultPublisher.assertCancelled()
                updatesPublisher.assertCancelled()
            }
        }

        @Nested
        @DisplayName("> Accept: application/json")
        inner class AcceptApplicationJson {
            private lateinit var result: FluxExchangeResult<AnwenderverzeichnisCollection>

            @BeforeEach
            fun perform() {
                val future = CompletableFuture.supplyAsync { AnwenderverzeichnisÜbersicht(anwenderverzeichnisId) }

                whenever(queryBus.query(WelcheAnwenderverzeichnisseGibtEs, AnwenderverzeichnisÜbersicht::class.java))
                    .thenReturn(future)

                result = client.withDecoder(decoder)
                    .get()
                    .uri("/api/anwenderverzeichnisse")
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .returnResult(AnwenderverzeichnisCollection::class.java)
            }

            @Test
            @DisplayName("< 200 OK")
            fun `liefert Response mit Status 200 OK`() {
                assertThat(result.status).isEqualTo(HttpStatus.OK)
            }

            @Test
            fun `liefert Collection der Anwenderverzeichnisse`() {
                result.responseBody.test()
                    .expectNext(AnwenderverzeichnisCollection(listOf(AnwenderverzeichnisResource(anwenderverzeichnisId))))
                    .thenCancel()
                    .verify()
            }
        }
    }
}