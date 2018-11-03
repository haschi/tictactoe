package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.events.SpielerHatDatingRoomBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.SpielerConverter
import com.github.haschi.tictactoe.requirements.core.testing.ZeichenConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat


class DatingSteps(private val welt: DieWelt) {

    @Angenommen("^die Anwender Martin und Matthias haben sich als Spielpartner gefunden$")
    fun die_Anwender_Martin_und_Matthias_haben_sich_als_Spielpartner_gefunden() {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus("Matthias", Spieler('X', "Matthias")))
                .thenCompose {
                    anwenderverzeichnis.send(WaehleZeichenAus("Martin", Spieler('O', "Martin")))
                }
        }
    }

    @Wenn("^ich (X|O) als mein Zeichen für die nächste Partie Tic Tac Toe aussuche$")
    fun ich_X_als_mein_Zeichen_für_die_nächste_Partie_Tic_Tac_Toe_aussuche(
        @Transform(ZeichenConverter::class) zeichen: Zeichen
    ) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(ich.name, Spieler(zeichen.wert, welt.ich.name)))
        }
    }

    @Wenn("^Die Anwenderin \"([^\"]*)\" den Dating Room als Spieler mit dem Zeichen O betritt$")
    fun die_Anwenderin_den_Dating_Room_als_Spieler_mit_dem_Zeichen_O_betritt(anwender: String) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(anwender, Spieler('O', anwender)))
        }
    }

    @Dann("^wird die Anwenderin \"([^\"]*)\" den Dating Room mit O betreten haben$")
    fun wird_die_Anwenderin_Maria_den_Dating_Room_mit_O_betreten_haben(anwender: String) {
        welt.future.get()
        assertThat(welt.events).contains(SpielerHatDatingRoomBetreten(anwender, Spieler('O', anwender)))
    }

    @Dann("^werde ich den Dating Room als Spieler mit (X|O) betreten haben$")
    fun werde_ich_den_Dating_Room_als_Spieler_mit_X_betreten_haben(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        welt.future.get()

        assertThat(welt.events).contains(
            SpielerHatDatingRoomBetreten(welt.ich.name, Spieler(spieler.zeichen, welt.ich.name))
        )
    }

    @Dann("^Dann werde ich auf einen Spieler warten, der (X|O) ausgesucht hat$")
    fun dann_werde_ich_auf_einen_Spieler_warten_der_O_ausgesucht_hat(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        println(spieler)
    }

    @Angenommen("^ich habe den Dating Room als Spieler mit dem Zeichen X betreten$")
    fun ich_habe_den_Dating_Room_als_Spieler_mit_dem_Zeichen_X_betreten() {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(ich.name, Spieler('X', ich.name)))
        }
    }

    @Wenn("^Der Anwender \"([^\"]*)\" den Dating Room als Spieler mit dem Zeichen O betritt$")
    fun der_Anwender_den_Dating_Room_als_Spieler_mit_dem_Zeichen_O_betritt(
        anwender: String
    ) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(anwender, Spieler('O', anwender)))
        }
    }

    @Dann("^werde ich mit \"([^\"]*)\" einen Spielpartner gefunden haben$")
    fun werde_ich_mit_einen_Spielpartner_gefunden_haben(anwender: String) {
        println(anwender)
        welt.future.get()

        assertThat(welt.events)
            .contains(SpielpartnerGefunden(Spieler('X', welt.ich.name), Spieler('O', anwender)))
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