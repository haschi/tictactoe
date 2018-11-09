package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
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
                tictactoe.send(SetzeZeichen(spielId, it))
            }
        }
    }

    @Dann("hat Spieler {spieler} gewonnen")
    fun `hat Spieler gewonnen`(spieler: Spieler) {
        welt.future.get()
        assertThat(welt.ereignisse)
            .contains(SpielGewonnen(welt.spielId, spieler))
    }

    @Dann("hat Spieler {spieler} nicht gewonnen")
    fun `hat Spieler nicht gewonnen`(spieler: Spieler) {
        assertThat(welt.ereignisse)
            .doesNotContain(SpielGewonnen(welt.spielId, spieler))
    }
}
