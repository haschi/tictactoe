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
import com.github.haschi.tictactoe.requirements.shared.testing.Resolver
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import domain.events.SpielUnentschiedenBeendet
import org.assertj.core.api.Assertions.assertThat

class ZeichenSetzenSteps(private val welt: DieWelt) {
    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen() {
        welt.compose {
            val spielId = Aggregatkennung()
            welt.tictactoe.send(
                BeginneSpiel(
                    spielId,
                    Spieler('X', "Matthias", Aggregatkennung()),
                    Spieler('O', "Martin", Aggregatkennung())
                )
            )
                .thenApply { copy(spielId = it) }
        }
    }

    @Angenommen("Spieler {spieler} hat sein Zeichen auf Feld {feld} gesetzt")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(spieler: Resolver<Spieler>, feld: Feld) {
        welt.compose {
            welt.tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler.resolve(this), feld)))
                .thenApply { this }
        }
    }

    @Wenn("Spieler {spieler} sein Zeichen auf Feld {feld} setzt")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(spieler: Resolver<Spieler>, feld: Feld) {
        welt.compose {
            welt.tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler.resolve(this), feld)))
                .thenApply { this }
        }
    }

    @Dann("werde ich den Spielzug {feld} von Spieler {spieler} akzeptiert haben")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(feld: Feld, spieler: Resolver<Spieler>) {
        welt.versuche {
            assertThat(it.ereignisse)
                .contains(SpielzugWurdeAkzeptiert(it.spielId, Spielzug(spieler.resolve(it), feld)))
        }
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(spieler: Resolver<Spieler>) {

        welt.exceptionally {
            assertThat(it.throwable).isEqualTo(FeldBelegt(it.resolve(spieler)))
            it.zustand
        }

        welt.zustand.get()
    }

    @Dann("konnte Spieler {spieler} sein Zeichen nicht platzieren, weil er nicht an der Reihe war")
    fun konnte_Spieler_X_sein_Zeichen_nicht_platzieren_weil_er_nicht_an_der_Reihe_war(spieler: Resolver<Spieler>) {
        welt.exceptionally {
            assertThat(it.throwable)
                .isEqualTo(SpielerNichtAndDerReiheGewesen(it.resolve(spieler)))
            it.zustand
        }

        welt.zustand.get()
    }

    @Dann("endet die Partie unentschieden")
    fun `Dann endet die Partie unentschieden`() {
        welt.versuche {
            assertThat(it.ereignisse)
                .contains(SpielUnentschiedenBeendet(it.spielId))
        }
    }
}
