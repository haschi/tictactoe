package com.github.haschi.tictactoe.backend

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.testing.FeldConverter
import com.github.haschi.tictactoe.domain.testing.SpielerConverter
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import cucumber.api.PendingException
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

@SpringBootTest(
    classes = [BackendApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(value = ["backend"])
class ZeichenSetzenSteps() {
    val restTemplate = RestTemplate()
    val server = "http://localhost"

    @LocalServerPort
    var port = 0

    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen() {
        val endpoint = server + ":" + port + "/api/command"

        restTemplate.postForEntity(
            endpoint,
            BeginneSpiel(Aggregatkennung()),
            Void::class.java
        )
    }

    @Wenn("^Spieler (X|O) sein Zeichen auf Feld ([ABC][123]) setzt$")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(
        @Transform(SpielerConverter::class) spieler: Spieler,
        @Transform(FeldConverter::class) feld: Feld
    ) {
        println(spieler)
        println(feld)
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Dann("^werde ich den Spielzug ([ABC][123]) von Spieler (X|O) akzeptiert haben$")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(
        @Transform(FeldConverter::class) feld: Feld,
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        println(feld)
        println(spieler)
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

}