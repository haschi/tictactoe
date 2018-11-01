package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.FeldConverter
import com.github.haschi.tictactoe.requirements.core.testing.SpielerConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter
import org.assertj.core.api.Assertions.assertThat
import java.util.*

class SpielendePrüfenSteps(private val welt: DieWelt) {
    @Angenommen("^ich habe folgenden Spielverlauf:$")
    fun `ich habe folgenden Spielverlauf`(spielverlauf: List<Spielzug>) {
        welt.next {
            spielId = Aggregatkennung(UUID.randomUUID())
            tictactoe.send(BeginneSpiel(welt.spielId))
        }

        spielverlauf.forEach {
            welt.next {
                tictactoe.send(
                    SetzeZeichen(
                        spielId,
                        com.github.haschi.tictactoe.domain.values.Spielzug(it.spieler, it.feld)
                    )
                )
            }
        }
    }

    @Dann("^hat Spieler (X|O) gewonnen$")
    fun `hat Spieler gewonnen`(@Transform(SpielerConverter::class) spieler: Spieler) {
        welt.future.get()
        assertThat(welt.events)
            .contains(SpielGewonnen(welt.spielId, spieler))
    }

    @Dann("^hat Spieler (X|O) nicht gewonnen$")
    fun `hat Spieler nicht gewonnen`(@Transform(SpielerConverter::class) spieler: Spieler) {
        assertThat(welt.events)
            .doesNotContain(SpielGewonnen(welt.spielId, spieler))
    }
}

data class Spielzug(
    @XStreamConverter(SpielerConverter::class) val spieler: Spieler,
    @XStreamConverter(FeldConverter::class) val feld: Feld
)
