package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.testing.DieWelt
import com.github.haschi.tictactoe.requirements.testing.FeldConverter
import com.github.haschi.tictactoe.requirements.testing.SpielerConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.assertj.core.api.Assertions.assertThat

class SpielendePruefen(private val welt: DieWelt)
{
    @Angenommen("^ich habe folgenden Spielverlauf:$")
    fun ich_habe_folgenden_Spielverlauf(arg1: List<Spielzug>)
    {
        welt.spielId = Aggregatkennung.neu()
        welt.send(BeginneSpiel(welt.spielId))

        arg1.forEach{
            welt.send(SetzeZeichen(welt.spielId, com.github.haschi.tictactoe.domain.values.Spielzug(it.spieler, it.feld)))
        }
    }

    @Dann("^hat Spieler (X|O) gewonnen$")
    fun `hat_Spieler gewonnen`(@Transform(SpielerConverter::class) spieler: Spieler)
    {
        assertThat(welt.events)
                .contains(SpielGewonnen(welt.spielId, spieler))
    }

    @Dann("^hat Spieler (X|O) nicht gewonnen$")
    fun `hat Spieler nicht gewonnen`(@Transform(SpielerConverter::class) spieler: Spieler)
    {
        assertThat(welt.events)
                .doesNotContain(SpielGewonnen(welt.spielId, spieler))
    }
}

data class Spielzug(
        @XStreamConverter(SpielerConverter::class) val spieler: Spieler,
        @XStreamConverter(FeldConverter::class) val feld: Feld)
