package com.github.haschi.tictactoe.requirements.backend

import com.github.haschi.tictactoe.backend.controller.SpielzugResource
import com.github.haschi.tictactoe.backend.controller.VndError
import com.github.haschi.tictactoe.backend.controller.VndError.Companion.ERROR_JSON_UTF8
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielfeld
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.reactive.function.client.WebClient
import java.util.*


@ActiveProfiles(value = ["backend", "axon"])
@ContextConfiguration
@SpringBootTest(classes = [TestApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZeichenSetzenSteps(
    @LocalServerPort port: Int,
    private val welt: RestWelt
) {

    val webClient = WebClient.builder()
        .baseUrl("http://localhost:$port")
        .build()

    @Angenommen("ich habe das Spiel begonnen")
    fun ich_habe_das_Spiel_begonnen() {
        val spielId = Aggregatkennung(UUID.randomUUID())

        welt.spiel = webClient.post()
            .uri("/api/spiel/$spielId")
            .exchange()
            .block()!!
            .headers()
            .asHttpHeaders()
            .location!!
    }

    @Angenommen("Spieler {spieler} hat sein Zeichen auf Feld {feld} gesetzt")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(spieler: Spieler, feld: Feld) {
        welt.spec = webClient.put()
            .uri(welt.spiel)
            .syncBody(SpielzugResource(spieler, feld))
            .accept(MediaType.APPLICATION_JSON_UTF8, ERROR_JSON_UTF8)
            .exchange()
            .block()!!
    }

    @Wenn("Spieler {spieler} sein Zeichen auf Feld {feld} setzt")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(spieler: Spieler, feld: Feld) {

        welt.spec = webClient.put()
            .uri(welt.spiel)
            .syncBody(SpielzugResource(spieler, feld))
            .accept(MediaType.APPLICATION_JSON_UTF8, ERROR_JSON_UTF8)
            .exchange()
            .block()!!
    }

    @Dann("werde ich den Spielzug {feld} von Spieler {spieler} akzeptiert haben")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(feld: Feld, spieler: Spieler) {
        val spielfeld = webClient.get().uri(welt.spiel)
            .accept(MediaTypes.HAL_JSON_UTF8)
            .retrieve()
            .bodyToMono(Spielfeld::class.java)
            .block()!!

        assertThat(spielfeld.inhalt(feld)).isEqualTo(spieler.zeichen)
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(spieler: Spieler) {
        assertThat(welt.spec.statusCode())
            .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)

        assertThat(welt.spec.bodyToMono(VndError::class.java).block())
            .isEqualTo(VndError("Feld belegt"))
    }
}