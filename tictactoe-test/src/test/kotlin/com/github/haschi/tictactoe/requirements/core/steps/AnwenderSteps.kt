package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import domain.events.VomSpielZurückgekehrt
import org.assertj.core.api.Assertions.assertThat

class AnwenderSteps(private val welt: DieWelt) {

    @Angenommen("ich habe den Warteraum als Spieler mit {zeichen} betreten")
    fun `Angenommen ich habe den Warteraum als Spieler mit zeichen betreten`(zeichen: Zeichen) {
        welt.compose {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.id, ich.name, zeichen))
                .thenApply { this }
        }
    }

    @Wenn("ich {zeichen} als mein Zeichen für die nächste Partie Tic Tac Toe aussuche")
    fun `Wenn ich mein Zeichen für die nächste Partie Tic Tac Toe aussuche`(zeichen: Zeichen) {
        welt.compose {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.id, ich.name, zeichen))
                .thenApply { this }
        }
    }

    @Wenn("ich die Partie gewinne")
    fun `Wenn ich die Partie gewinne`() {
        welt.compose {
            val matthias = spieler['X']!!
            val martin = spieler['O']!!
            listOf(
                SetzeZeichen(spielId, Spielzug(matthias, Feld('B', 2))),
                SetzeZeichen(spielId, Spielzug(martin, Feld('B', 1))),
                SetzeZeichen(spielId, Spielzug(matthias, Feld('A', 1))),
                SetzeZeichen(spielId, Spielzug(martin, Feld('C', 1))),
                SetzeZeichen(spielId, Spielzug(matthias, Feld('C', 3)))
            )
                .map {
                    welt.tictactoe.send(it)
                        .thenApply { this }
                }.reduce { l, r -> l.thenCompose { r } }
        }
    }

    @Dann("^werde ich eine Fehlermeldung erhalten:$")
    fun `Dann werde ich eine Fehlermeldung erhalten`(meldung: String) {

        welt.exceptionally {
            assertThat(it.throwable).hasMessage(meldung)
            it.zustand
        }

        welt.zustand.get()
    }

    @Dann("werde ich kein Zeichen ausgesucht haben")
    fun `Dann werde ich kein Zeichen ausgesucht haben`() {
        welt.join {
            assertThat(zustand.ereignisse)
                .contains(VomSpielZurückgekehrt(zustand.ich.id))
        }
    }
}