package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.events.SpielerNichtAndDerReiheGewesen
import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import java.util.*

class ZeichenSetzenSteps(private val welt: DieWelt) {
    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen() {
        welt.next {
            spielId = Aggregatkennung(UUID.randomUUID())
            tictactoe.send(BeginneSpiel(spielId))
        }
    }

    @Angenommen("Spieler {spieler} hat sein Zeichen auf Feld {feld} gesetzt")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(spieler: Spieler, feld: Feld) {
        welt.next {
            tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler, feld)))
        }
    }

    @Wenn("Spieler {spieler} sein Zeichen auf Feld {feld} setzt")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(spieler: Spieler, feld: Feld) {
        welt.next {
            tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler, feld)))
        }
    }

    @Dann("werde ich den Spielzug {feld} von Spieler {spieler} akzeptiert haben")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(feld: Feld, spieler: Spieler) {
        welt.future.get()

        assertThat(welt.ereignisse).contains(
            SpielzugWurdeAkzeptiert(welt.spielId, Spielzug(spieler, feld))
        )
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(spieler: Spieler) {
        assertThat(welt.future)
            .hasFailedWithThrowableThat()
            .isEqualTo(FeldBelegt(welt.spielId, spieler))
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil er nicht an der Reihe war")
    fun konnte_Spieler_X_sein_Zeichen_nicht_platzieren_weil_er_nicht_an_der_Reihe_war(spieler: Spieler) {
        assertThat(welt.future)
            .hasFailedWithThrowableThat()
            .isEqualTo(SpielerNichtAndDerReiheGewesen(welt.spielId, spieler))
    }
}
