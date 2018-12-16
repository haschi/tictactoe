package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.AuswahlNichtMöglich
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt

import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat

class AnwenderSteps(private val welt: DieWelt) {

    @Wenn("ich {zeichen} als mein Zeichen für die nächste Partie Tic Tac Toe aussuche")
    fun `Wenn ich mein Zeichen für die nächste Partie Tic Tac Toe aussuche`(zeichen: Zeichen) {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.name, zeichen))
                .thenApply { this }
        }
    }

    @Dann("^werde ich eine Fehlermeldung erhalten:$")
    fun `Dann werde ich eine Fehlermeldung erhalten`(meldung: String) {
        welt.join {
            assertThat(welt.zustand)
                .hasFailedWithThrowableThat()
                .isInstanceOf(AuswahlNichtMöglich::class.java)
                .hasMessage(meldung)
        }
    }
}