package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
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
import org.springframework.web.util.NestedServletException
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
    lateinit var commandGateway: TicTacToeGateway

    @MockBean
    lateinit var queryGateway: QueryGateway

    @Test
    fun `Beginne Spiel liefert Response mit Status 201 Created`() {
        val spielId = Aggregatkennung()
        val command = BeginneSpiel(spielId)
        val future = CompletableFuture<Aggregatkennung>()
        future.complete(spielId)

        whenever(commandGateway.send(command, spielId.toString()))
            .thenReturn(future)

        mvc.perform(
            post(URI("/api/spiel/$spielId"))
        )
            .andExpect(status().isCreated)
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/spiel/$spielId"))
    }

    @Test
    fun `Feld belegt führt zu Response mit Status 422 Unprocessable Entity`() {

        val spielId = Aggregatkennung()
        val command = SetzeZeichen(spielId, Spielzug(Spieler('X'), Feld('A', 1)))
        val future = CompletableFuture<Void>()
        val resource = SpielzugResource(command.spielzug.spieler, command.spielzug.feld)

        future.completeExceptionally(FeldBelegt(spielId, Spieler('X')))

        whenever(commandGateway.send(command, spielId.toString()))
            .thenReturn(future)

        Assertions.assertThatCode {
            val result = mvc.perform(
                put(URI("/api/spiel/${spielId}"))
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsString(resource))
            )
                .andDo { println(it.asyncResult) }
                .andExpect(request().asyncStarted())
                .andReturn()

            mvc.perform(asyncDispatch(result))
        }.isInstanceOf(NestedServletException::class.java)
            .hasCause(FeldBelegt(spielId, Spieler('X')))
    }
}