package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.hateoas.MediaTypes.HAL_JSON_UTF8
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.net.URI
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebMvcTest(AnwenderverzeichnisController::class)
@ActiveProfiles("test")
open class AnwenderverzeichnisControllerTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {

    @MockBean
    private lateinit var anwenderverzeichnis: AnwenderverzeichnisGateway

    @Test
    fun `Anwenderverzeichnis anlegen liefert Response mit Status 201 Created`() {
        val anwenderverzeichnisId = Aggregatkennung()

        val legeAnwenderverzeichnisAn = LegeAnwenderverzeichnisAn(anwenderverzeichnisId)
        val future = CompletableFuture<Aggregatkennung>()
        future.complete(anwenderverzeichnisId)

        whenever(this.anwenderverzeichnis.send(legeAnwenderverzeichnisAn))
            .thenReturn(future)

        val params = mapper.writeValueAsString(legeAnwenderverzeichnisAn)
        val result = mvc.perform(
            post(URI("/api/anwenderverzeichnis"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .content(params)
        )
            .andExpect(request().asyncStarted()).andReturn()

        mvc.perform(asyncDispatch(result))
            .andExpect(status().isCreated)
            .andExpect(
                header().string(
                    HttpHeaders.LOCATION,
                    "http://localhost/api/anwenderverzeichnis/$anwenderverzeichnisId"
                )
            )
    }

    @Nested
    @DisplayName("GET /api/anwenderverzeichnis")
    inner class GetApiAnwenderverzeichnis {

        private lateinit var result: ResultActions

        @BeforeEach
        fun perform() {
            val x = mvc.perform(
                `get`(URI("/api/anwenderverzeichnis"))
                    .accept(HAL_JSON_UTF8)
            )
                .andExpect(request().asyncStarted())
                .andReturn()

            result = mvc.perform(asyncDispatch(x))
        }

        @Test
        fun `liefert Response mit Status 200 OK`() {
            result.andExpect(status().isOk)
        }

        @Test
        fun `liefert Content-Type application|hal+json|charset=UTF-8`() {

            result.andExpect(content().contentType(HAL_JSON_UTF8))
        }

        @Test
        fun `liefert self link`() {
            result
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/anwenderverzeichnis"))
        }
    }
}