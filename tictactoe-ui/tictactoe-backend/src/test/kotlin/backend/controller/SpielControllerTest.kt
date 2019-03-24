package com.github.haschi.tictactoe.backend.controller


import backend.controller.withDecoder
import backend.controller.withEncoder
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.backend.WebFluxConfiguration
import com.github.haschi.tictactoe.backend.marshalling.CodecsConfiguration
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.values.*
import com.nhaarman.mockitokotlin2.whenever
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [SpielController::class])
@Import(
    WebFluxConfiguration::class,
    CodecsConfiguration::class
)
@ActiveProfiles("test")
class SpielControllerTest(
    @Autowired private val decoder: Jackson2JsonDecoder,
    @Autowired private val encoder: Jackson2JsonEncoder
) {
    @Autowired
    lateinit var client: WebTestClient

    @MockBean
    private lateinit var command: TicTacToeGateway

    @MockBean
    private lateinit var query: QueryGateway

    @Test
    fun `Beginne Spiel liefert Response mit Status 201 Created`() {
        val spielId = Aggregatkennung()

        val spieler = SpielerParameter(
            Spieler('X', "Matthias", Aggregatkennung()),
            Spieler('O', "Martin", Aggregatkennung())
        )

        val beginneSpiel = BeginneSpiel(spielId, spieler.x, spieler.o)
        val future = CompletableFuture<Aggregatkennung>()
        future.complete(spielId)

        whenever(this.command.send(beginneSpiel))
            .thenReturn(future)

        client
            .withEncoder(encoder)
            .post()
            .uri("/api/spiel/$spielId")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromPublisher(Mono.just(spieler), SpielerParameter::class.java))
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(HttpHeaders.LOCATION, "/api/spiel/$spielId")
    }

    @Test
    fun `Feld belegt führt zu Response mit Status 422 Unprocessable Entity`() {

        val spielId = Aggregatkennung(UUID.randomUUID())

        val setzeZeichen = SetzeZeichen(
            spielId,
            Spielzug(Spieler('X', "", Aggregatkennung()), Feld('A', 1))
        )

        val future = CompletableFuture<Void>()
        val resource = SpielzugResource(setzeZeichen.spielzug.spieler, setzeZeichen.spielzug.feld)

        future.completeExceptionally(FeldBelegt(Spieler('X', "", setzeZeichen.spielzug.spieler.anwenderId)))

        whenever(this.command.send(setzeZeichen))
            .thenReturn(future)

        client.withEncoder(encoder)
            .put()
            .uri("/api/spiel/$spielId")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON, VndError.ERROR_JSON_UTF8)
            .body(BodyInserters.fromPublisher(Mono.just(resource), SpielzugResource::class.java))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().json(
                """
                    {
                        "message": "Feld belegt"
                    }
                """.trimIndent()
            )

    }

    @Test
    fun `GET Spielfeld liefert Status 200 OK`() {
        val spielId = Aggregatkennung(UUID.randomUUID())
        val spielfeld = Spielfeld(emptyList())
        val future = CompletableFuture<Spielfeld>()
        future.complete(spielfeld)

        whenever(query.query(SpielfeldQuery(spielId), Spielfeld::class.java))
            .thenReturn(future)

        client.withDecoder(decoder)
            .get()
            .uri("/api/spiel/$spielId")
            .exchange()
            .expectStatus().isOk
    }
}