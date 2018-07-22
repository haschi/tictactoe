

package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.events.SpielerNichtAndDerReiheGewesen
import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.requirements.testing.DieWelt
import com.github.haschi.tictactoe.requirements.testing.FeldConverter
import com.github.haschi.tictactoe.requirements.testing.SpielerConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat

class ZeichenSetzenSteps(private val welt: DieWelt)
{
    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen()
    {
        welt.spielId = Aggregatkennung.neu()
        welt.send(BeginneSpiel(welt.spielId))
    }

    @Angenommen("^Spieler (X|O) hat sein Zeichen auf Feld ([ABC][123]) gesetzt$")
    fun spieler_X_hat_sein_Zeichen_auf_Feld_B_gesetzt(
            @Transform(SpielerConverter::class) spieler: Spieler,
            @Transform(FeldConverter::class) feld: Feld)
    {
        welt.next {
            tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler, feld)))
        }
    }

    @Wenn("^Spieler (X|O) sein Zeichen auf Feld ([ABC][123]) setzt$")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(
            @Transform(SpielerConverter::class) spieler: Spieler,
            @Transform(FeldConverter::class) feld: Feld)
    {
        welt.next {
            tictactoe.send(SetzeZeichen(spielId, Spielzug(spieler, feld)))
        }
    }

    @Dann("^werde ich den Spielzug ([ABC][123]) von Spieler (X|O) akzeptiert haben$")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(
            @Transform(FeldConverter::class) feld: Feld,
            @Transform(SpielerConverter::class) spieler: Spieler)
    {
        welt.future.get()

        assertThat(welt.events).contains(
                SpielzugWurdeAkzeptiert(welt.spielId, Spielzug(spieler, feld)))
    }

    @Dann("^konnte Spieler (X|O) sein Zeichen nicht platzieren, weil das Feld belegt gewesen ist$")
    fun konnte_Spieler_O_sein_Zeichen_nicht_platzieren_weil_das_Feld_belegt_gewesen_ist(
            @Transform(SpielerConverter::class) spieler: Spieler)
    {
        assertThat(welt.future)
                .hasFailedWithThrowableThat()
                .isEqualTo(FeldBelegt(welt.spielId, spieler))
    }

    @Dann("^konnte Spieler (X|O) sein Zeichen nicht platzieren, weil er nicht an der Reihe war$")
    fun konnte_Spieler_X_sein_Zeichen_nicht_platzieren_weil_er_nicht_an_der_Reihe_war(
            @Transform(SpielerConverter::class) spieler: Spieler)
    {
        assertThat(welt.future)
                .hasFailedWithThrowableThat()
                .isEqualTo(SpielerNichtAndDerReiheGewesen(welt.spielId, spieler))
    }
}
