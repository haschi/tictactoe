package com.github.haschi.tictactoe.requirements.backend

import com.github.haschi.tictactoe.backend.controller.SpielzugResource
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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestOperations
import java.util.*

@ActiveProfiles(value = ["backend"])
@ContextConfiguration
@SpringBootTest(classes = [TestApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
class ZeichenSetzenSteps(
    private val restTemplate: RestOperations,
    private val welt: RestWelt
) {

    val server = "http://localhost"

    @LocalServerPort
    var port = 0

    @Angenommen("ich habe das Spiel begonnen")
    fun ich_habe_das_Spiel_begonnen() {
        val spielId = Aggregatkennung(UUID.randomUUID())
        val endpoint = "$server:$port/api/spiel/$spielId"

        welt.spiel = restTemplate.postForLocation(endpoint, null)!!
    }

    @Angenommen("Spieler {spieler} hat sein Zeichen auf Feld {feld} gesetzt")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(spieler: Spieler, feld: Feld) {
        restTemplate.put(welt.spiel, SpielzugResource(spieler, feld))
    }

    @Wenn("Spieler {spieler} sein Zeichen auf Feld {feld} setzt")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(spieler: Spieler, feld: Feld) {
        try {
            restTemplate.put(welt.spiel, SpielzugResource(spieler, feld))
        } catch (error: HttpStatusCodeException) {
            welt.error = error
        }
    }

    @Dann("werde ich den Spielzug {feld} von Spieler {spieler} akzeptiert haben")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(feld: Feld, spieler: Spieler) {
        val spielfeld = restTemplate.getForObject(welt.spiel, Spielfeld::class.java)!!
        assertThat(spielfeld.inhalt(feld)).isEqualTo(spieler.zeichen)
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(spieler: Spieler) {
        assertThat(welt.error)
            .isInstanceOf(HttpClientErrorException::class.java)
    }
}