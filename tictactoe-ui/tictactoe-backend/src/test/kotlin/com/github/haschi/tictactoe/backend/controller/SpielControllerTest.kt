package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.values.*
import com.nhaarman.mockito_kotlin.whenever
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.net.URI
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebMvcTest(SpielController::class)
@ActiveProfiles("test")
open class SpielControllerTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {
    @MockBean
    private
    lateinit var command: TicTacToeGateway

    @MockBean
    private
    lateinit var query: QueryGateway

    @Test
    fun `Beginne Spiel liefert Response mit Status 201 Created`() {
        val spielId = Aggregatkennung()
        val beginneSpiel = BeginneSpiel(spielId)
        val future = CompletableFuture<Aggregatkennung>()
        future.complete(spielId)

        whenever(this.command.send(beginneSpiel, spielId.toString()))
            .thenReturn(future)

        val result = mvc.perform(
            post(URI("/api/spiel/$spielId"))
        ).andExpect(request().asyncStarted()).andReturn()

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isCreated)
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/spiel/$spielId"))
    }

    @Test
    fun `Feld belegt führt zu Response mit Status 422 Unprocessable Entity`() {

        val spielId = Aggregatkennung()
        val setzeZeichen = SetzeZeichen(spielId, Spielzug(Spieler('X'), Feld('A', 1)))
        val future = CompletableFuture<Void>()
        val resource = SpielzugResource(setzeZeichen.spielzug.spieler, setzeZeichen.spielzug.feld)

        future.completeExceptionally(FeldBelegt(spielId, Spieler('X')))

        whenever(this.command.send(setzeZeichen, spielId.toString()))
            .thenReturn(future)

        val result = mvc.perform(
            put(URI("/api/spiel/$spielId"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(VndError.mediaType, MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(resource))
        )
            .andExpect(request().asyncStarted())
            .andReturn()

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentType(VndError.mediaType))
            .andExpect(
                content().json(
                    """
                    {
                        "message": "Feld belegt"
                    }
                """.trimIndent()
                )
            )
    }

    @Test
    fun `GET Spielfeld liefert Status 200 OK`() {
        val spielId = Aggregatkennung()
        val spielfeld = Spielfeld(emptyList())
        val future = CompletableFuture<Spielfeld>()
        future.complete(spielfeld)

        whenever(query.query(SpielfeldQuery(spielId), Spielfeld::class.java))
            .thenReturn(future)

        val result = mvc.perform(
            `get`(URI("/api/spiel/$spielId"))
        )
            .andExpect(request().asyncStarted())
            .andReturn()

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    }
}