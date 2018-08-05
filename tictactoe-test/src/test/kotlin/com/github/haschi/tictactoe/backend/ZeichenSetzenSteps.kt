package com.github.haschi.tictactoe.backend

import com.github.haschi.tictactoe.TestApplication
import com.github.haschi.tictactoe.backend.controller.SpielResource
import com.github.haschi.tictactoe.backend.controller.SpielzugResource
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.testing.FeldConverter
import com.github.haschi.tictactoe.domain.testing.SpielerConverter
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielfeld
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestOperations

@SpringBootTest(
    // classes = [TicTacToeBackendApplication::class],
    classes = [TestApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(value = ["backend"])
class ZeichenSetzenSteps(private val restTemplate: RestOperations, private val welt: RestWelt) {
    val server = "http://localhost"

    @LocalServerPort
    var port = 0

    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen() {
        val endpoint = "$server:$port/api/spiel"

        val resource = SpielResource(Aggregatkennung())
        welt.spiel = restTemplate.postForLocation(endpoint, resource)!!
    }

    @Angenommen("^Spieler (X|O) hat sein Zeichen auf Feld ([ABC][123]) gesetzt$")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(
        @Transform(SpielerConverter::class) spieler: Spieler,
        @Transform(FeldConverter::class) feld: Feld
    ) {
        restTemplate.put(welt.spiel, SpielzugResource(spieler, feld))
    }

    @Wenn("^Spieler (X|O) sein Zeichen auf Feld ([ABC][123]) setzt$")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(
        @Transform(SpielerConverter::class) spieler: Spieler,
        @Transform(FeldConverter::class) feld: Feld
    ) {
        try {
            restTemplate.put(welt.spiel, SpielzugResource(spieler, feld))
        } catch (error: HttpServerErrorException) {
            welt.error = error
        }
    }

    @Dann("^werde ich den Spielzug ([ABC][123]) von Spieler (X|O) akzeptiert haben$")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(
        @Transform(FeldConverter::class) feld: Feld,
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        val spielfeld = restTemplate.getForObject(welt.spiel, Spielfeld::class.java)!!
        assertThat(spielfeld.inhalt(feld)).isEqualTo(spieler.zeichen)
    }

    @Dann("^konnte Spieler (X|O) sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist$")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        assertThat(welt.error)
            .isInstanceOf(FeldBelegt::class.java)
    }
}