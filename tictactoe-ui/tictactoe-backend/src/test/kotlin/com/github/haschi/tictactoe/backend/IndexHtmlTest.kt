package com.github.haschi.tictactoe.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class IndexHtmlTest(@Autowired private val client: WebTestClient) {

    @Test
    fun `Wuzel URL zeigt Startseite`() {
        client.get().uri("/")
            .accept(MediaType.TEXT_HTML)
            .exchange()
            .expectStatus().is2xxSuccessful
    }
}