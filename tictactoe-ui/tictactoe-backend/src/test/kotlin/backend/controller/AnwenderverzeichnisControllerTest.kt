package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.nhaarman.mockito_kotlin.whenever
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.hateoas.MediaTypes.HAL_JSON_UTF8
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebMvcTest(AnwenderverzeichnisCollectionController::class)
@Import(AnwenderverzeichnisController::class)
@ActiveProfiles("test")
@DirtiesContext
open class AnwenderverzeichnisControllerTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {

    @MockBean
    private lateinit var anwenderverzeichnis: AnwenderverzeichnisGateway

    @MockBean
    private lateinit var queryBus: QueryGateway

    @Nested
    @DisplayName("POST /api/anwenderverzeichnisse")
    inner class PostApiAnwenderverzeichnis {

        val anwenderverzeichnisId = Aggregatkennung()
        private lateinit var result: ResultActions
        @BeforeEach
        fun perform() {

            val legeAnwenderverzeichnisAn = LegeAnwenderverzeichnisAn(anwenderverzeichnisId)
            val future = CompletableFuture<Aggregatkennung>()
            future.complete(anwenderverzeichnisId)

            whenever(anwenderverzeichnis.send(legeAnwenderverzeichnisAn))
                .thenReturn(future)

            val params = mapper.writeValueAsString(legeAnwenderverzeichnisAn)
            val r = mvc.perform(
                post("/api/anwenderverzeichnisse")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .characterEncoding("UTF-8")
                    .content(params)
            )
                .andExpect(request().asyncStarted()).andReturn()

            result = mvc.perform(asyncDispatch(r))
        }

        @Test
        fun `liefert Status 201 Created`() {
            result.andExpect(status().isCreated)
        }

        @Test
        fun `liefert Location des neuen Anwenderverzeichnisses`() {
            result.andExpect(
                header().string(
                    HttpHeaders.LOCATION,
                    "http://localhost/api/anwenderverzeichnisse/$anwenderverzeichnisId"
                )
            )
        }
    }

    @Nested
    @DisplayName("GET /api/anwenderverzeichnis")
    inner class GetApiAnwenderverzeichnis {

        private lateinit var result: ResultActions

        private val anwenderverzeichnisId = Aggregatkennung()

        @BeforeEach
        fun perform() {

            val future = CompletableFuture<AnwenderverzeichnisÜbersicht>()
            future.complete(AnwenderverzeichnisÜbersicht(anwenderverzeichnisId))

            whenever(queryBus.query(WelcheAnwenderverzeichnisseGibtEs, AnwenderverzeichnisÜbersicht::class.java))
                .thenReturn(future)

            val request = mvc.perform(
                `get`("/api/anwenderverzeichnisse")
                    .accept(HAL_JSON_UTF8)
            )
                .andExpect(request().asyncStarted())
                .andReturn()

            result = mvc.perform(asyncDispatch(request))
        }

        @Test
        fun `liefert Response mit Status 200 OK`() {
            result.andExpect(status().isOk)
        }

        @Test
        @DisplayName("liefert Content-Type application/hal+json;charset=UTF-8")
        fun `liefert Content-Type application|hal+json|charset=UTF-8`() {
            result.andExpect(content().contentType(HAL_JSON_UTF8))
        }

        @Test
        fun `liefert self link der Collection`() {
            result
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/anwenderverzeichnisse"))
        }

        @Test
        fun `liefert self links für jedes Element der Collection`() {
            result
                .andDo { println(it.response.contentAsString) }
                .andExpect(
                    jsonPath("$._embedded.anwenderverzeichnisse[0]._links.self.href")
                        .value("http://localhost/api/anwenderverzeichnisse/$anwenderverzeichnisId")
                )
        }

        @Test
        fun `liefert Collection der Anwendungsverzeichnisse`() {
            result
                .andDo { println(it.response.contentAsString) }
                .andExpect(jsonPath("$._embedded.anwenderverzeichnisse[0].id").value(anwenderverzeichnisId.id))
        }
    }
}