package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import java.net.URI

@ExtendWith(SpringExtension::class)
@WebMvcTest(AnwenderverzeichnisController::class)
@ActiveProfiles("test")
open class AnwenderverzeichnisControllerTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {
    @Test
    fun `Anwenderverzeichnis anlegen liefert Response mit Status 201 Created`() {
        val anwenderverzeichnisId = Aggregatkennung()

        val legeAnwenderverzeichnisAn = LegeAnwenderverzeichnisAn(anwenderverzeichnisId)
        val params = mapper.writeValueAsString(legeAnwenderverzeichnisAn)
        mvc.perform(
            post(URI("/api/anwenderverzeichnis"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8")
                .content(params)
        )
            .andExpect(request().asyncStarted()).andReturn()
    }
}