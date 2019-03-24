package com.github.haschi.tictactoe.requirements.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.backend.controller.SpielerParameter
import com.github.haschi.tictactoe.backend.controller.SpielzugResource
import com.github.haschi.tictactoe.backend.controller.VndError
import com.github.haschi.tictactoe.backend.controller.VndError.Companion.ERROR_JSON_UTF8
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielfeld
import com.github.haschi.tictactoe.requirements.shared.testing.Resolver
import cucumber.api.PendingException
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.opentest4j.AssertionFailedError
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@ActiveProfiles(value = ["backend"])
@ContextConfiguration
@SpringBootTest(classes = [TestApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZeichenSetzenSteps(
    @LocalServerPort val port: Int,
    private val welt: RestWelt,
    val mapper: ObjectMapper
) {

    var strategies = ExchangeStrategies
        .builder()
        .codecs { clientDefaultCodecsConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonEncoder(
                    Jackson2JsonEncoder(
                        mapper,
                        MediaType.APPLICATION_JSON,
                        ERROR_JSON_UTF8
                    )
                )
            clientDefaultCodecsConfigurer.defaultCodecs()
                .jackson2JsonDecoder(
                    Jackson2JsonDecoder(
                        mapper, MediaType.APPLICATION_JSON, ERROR_JSON_UTF8
                    )
                )

        }.build()

    val webClient = WebClient.builder()
        .baseUrl("http://localhost:$port")
        .exchangeStrategies(strategies)
        .build()

    @Angenommen("ich habe das Spiel begonnen")
    fun ich_habe_das_Spiel_begonnen() {
        val spielId = Aggregatkennung(UUID.randomUUID())

        val parameter = SpielerParameter(
            Spieler('X', "Matthias", Aggregatkennung()),
            Spieler('O', "Martin", Aggregatkennung())
        )

        welt.spieler = welt.spieler + ('X' to parameter.x)
        welt.spieler = welt.spieler + ('O' to parameter.o)

        val response = webClient.post()
            .uri("/api/spiel/$spielId")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(parameter))
            .exchange()
            .log()
            .block()!!

        assert(response.statusCode().is2xxSuccessful) {
            """
                Unerwartetes Ergebnis: Status Code ${response.rawStatusCode()}.
                ${response.bodyToMono(String::class.java).block()}
                ${response.statusCode().reasonPhrase}
            """
        }

        welt.spiel = response.headers()
            .asHttpHeaders()
            .location!!
    }

    @Angenommen("Spieler {spieler} hat sein Zeichen auf Feld {feld} gesetzt")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(spieler: Resolver<Spieler>, feld: Feld) {
        PendingException("Nicht implementiert")
        welt.spec = webClient.put()
            .uri(welt.spiel.toString())
            //.uri("http://localhost:${port}/${welt.spiel}")

            .syncBody(SpielzugResource(spieler.resolve(welt), feld))
            .accept(MediaType.APPLICATION_JSON_UTF8, ERROR_JSON_UTF8)
            .exchange()
            .block()!!
    }

    @Wenn("Spieler {spieler} sein Zeichen auf Feld {feld} setzt")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(spieler: Resolver<Spieler>, feld: Feld) {

        welt.spec = webClient.put()
            .uri(welt.spiel.toString())
            .syncBody(SpielzugResource(spieler.resolve(welt), feld))
            .accept(MediaType.APPLICATION_JSON_UTF8, ERROR_JSON_UTF8)
            .exchange()
            .block()!!
    }

    @Dann("werde ich den Spielzug {feld} von Spieler {spieler} akzeptiert haben")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(feld: Feld, spieler: Resolver<Spieler>) {

        val s = spieler.resolve(welt)

        versucheBisEsKlappt {
            val spielfeld = webClient.get().uri(welt.spiel.toString())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Spielfeld::class.java)
                .block()!!

            assertThat(spielfeld.inhalt(feld)).isEqualTo(s.zeichen)
        }
    }

    private fun versucheBisEsKlappt(block: () -> Unit) {
        var versuch = 0
        while (versuch < 10) {
            try {
                block()
                versuch = 10
            } catch (e: AssertionFailedError) {
                versuch++
                if (versuch == 10) {
                    throw e
                }
                val wartezeit = (10 - versuch) * 100L
                Thread.sleep(wartezeit)
            }
        }
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(spieler: Resolver<Spieler>) {

        assertThat(welt.spec.bodyToMono(VndError::class.java).block())
            .isEqualTo(VndError("Feld belegt"))
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil er nicht an der Reihe war")
    fun konnte_Spieler_X_sein_Zeichen_nicht_platzieren_weil_er_nicht_an_der_Reihe_war(spieler: Resolver<Spieler>) {
        assertThat(welt.spec.bodyToMono(VndError::class.java).block())
            .isEqualTo(VndError("Spieler ist nicht an der Reihe"))
    }

    companion object : KLogging()
}