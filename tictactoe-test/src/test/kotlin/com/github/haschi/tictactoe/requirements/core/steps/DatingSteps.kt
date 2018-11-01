package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.events.SpielerHatDatingRoomBetreten
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.SpielerConverter
import com.github.haschi.tictactoe.requirements.core.testing.ZeichenConverter
import cucumber.api.Transform
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat


class DatingSteps(private val welt: DieWelt) {
    @Wenn("^ich (X|O) als mein Zeichen für die nächste Partie Tic Tac Toe aussuche$")
    fun ich_X_als_mein_Zeichen_für_die_nächste_Partie_Tic_Tac_Toe_aussuche(
        @Transform(ZeichenConverter::class) zeichen: Zeichen
    ) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(ich.name, Spieler(zeichen.wert)))
        }
    }

    @Dann("^werde ich den Dating Room als Spieler mit (X|O) betreten haben$")
    fun werde_ich_den_Dating_Room_als_Spieler_mit_X_betreten_haben(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        welt.future.get()

        assertThat(welt.events).contains(
            SpielerHatDatingRoomBetreten(welt.ich.name, spieler)
        )
    }

    @Dann("^Dann werde ich auf einen Spieler warten, der (X|O) ausgesucht hat$")
    fun dann_werde_ich_auf_einen_Spieler_warten_der_O_ausgesucht_hat(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        println(spieler)
    }

//    @Wenn("^ich (X|O) als mein Zeichen für die nächste Partie Tic Tac Toe aussuche$")
//    fun ich_O_als_mein_Zeichen_für_die_nächste_Partie_Tic_Tac_Toe_aussuche(zeichen: Zeichen) {
//        println(zeichen)
//        // Write code here that turns the phrase above into concrete actions
//        throw PendingException()
//    }

//    @Dann("^Dann werde ich auf einen Spieler warten, der (X|O) ausgesucht hat$")
//    fun dann_werde_ich_auf_einen_Spieler_warten_der_X_ausgesucht_hat(zeichen: Zeichen) {
//        println(zeichen)
//        // Write code here that turns the phrase above into concrete actions
//        throw PendingException()
//    }
}