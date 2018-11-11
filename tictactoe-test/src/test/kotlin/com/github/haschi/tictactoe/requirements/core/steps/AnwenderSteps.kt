package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.ZeichenConverter
import cucumber.api.Transform
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat


class AnwenderSteps(private val welt: DieWelt) {

    @Wenn("^ich (X|O) als mein Zeichen für die nächste Partie Tic Tac Toe aussuche$")
    fun `Wenn ich mein Zeichen für die nächste Partie Tic Tac Toe aussuche`(
        @Transform(ZeichenConverter::class) zeichen: Zeichen
    ) {
        welt.next { anwenderverzeichnis.send(WähleZeichenAus(welt.ich.name, Spieler(zeichen.wert, welt.ich.name))) }
    }

    @Dann("^werde ich eine Fehlermeldung erhalten$")
    fun `Dann werde ich eine Fehlermeldung erhalten`() {
        assertThat(welt.future).isCompletedExceptionally
    }
}