package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.Warteraum
import com.github.haschi.tictactoe.domain.commands.LegeMaximaleWartezeitFest
import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.events.MaximaleWartezeitFestgelegt
import com.github.haschi.tictactoe.domain.events.SpielerHatWarteraumBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.events.WarteraumVerlassen
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.SpielerConverter
import com.github.haschi.tictactoe.requirements.core.testing.ZeichenConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import java.time.Duration


class WarteraumSteps(private val welt: DieWelt) {

    @Angenommen("^ich habe eine maximale Wartezeit von (\\d+) Millisekunden für den Warteraum festgelegt$")
    fun ich_habe_eine_maximale_Wartezeit_von_Millisekunden_für_den_Warteraum_festgelegt(wartezeit: Long) {
        welt.next {
            warteraum.send(
                LegeMaximaleWartezeitFest(
                    Warteraum.ID,
                    Duration.ofMillis(wartezeit)
                )
            )
        }
    }

    @Angenommen("^die Anwender Martin und Matthias haben sich als Spielpartner gefunden$")
    fun die_Anwender_Martin_und_Matthias_haben_sich_als_Spielpartner_gefunden() {
        welt.schritt(
            { anwenderverzeichnis.send(WaehleZeichenAus("Matthias", Spieler('X', "Matthias"))) },
            { anwenderverzeichnis.send(WaehleZeichenAus("Martin", Spieler('O', "Martin"))) }
        )
    }

    @Angenommen("^ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht$")
    fun ich_habe_X_als_mein_Zeichen_für_die_nächste_Partie_Tic_Tac_Toe_ausgesucht() {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(ich.name, Spieler('X', ich.name)))
        }
    }

    @Wenn("^ich die maximale Wartezeit überschritten habe$")
    fun ich_die_maximale_Wartezeit_überschritten_habe() {

        welt {
            future.get()
            val maximaleWartezeit =
                ereignisse.filterIsInstance(MaximaleWartezeitFestgelegt::class.java)
                    .map { it.wartezeit.toMillis() }
                    .last()

            Thread.sleep(maximaleWartezeit + 100)
        }
    }

    @Wenn("^Wenn nach fünf Minuten kein Spieler O als sein Zeichen ausgesucht hat$")
    fun wenn_nach_fünf_Minuten_kein_Spieler_O_als_sein_Zeichen_ausgesucht_hat() {
    }

    @Dann("^werde ich den Warteraum ohne Spielpartner verlassen haben$")
    fun werde_ich_den_Warteraum_ohne_Spielpartner_verlassen_haben() {
        welt {
            tatsachen bestätigen WarteraumVerlassen(ich.name)
        }
    }

    @Wenn("^ich (X|O) als mein Zeichen für die nächste Partie Tic Tac Toe aussuche$")
    fun ich_X_als_mein_Zeichen_für_die_nächste_Partie_Tic_Tac_Toe_aussuche(
        @Transform(ZeichenConverter::class) zeichen: Zeichen
    ) {
        welt.next { anwenderverzeichnis.send(WaehleZeichenAus(welt.ich.name, Spieler(zeichen.wert, welt.ich.name))) }
    }

    @Wenn("^Die Anwenderin \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun die_Anwenderin_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(anwender: String) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(anwender, Spieler('O', anwender)))
        }
    }

    @Dann("^wird die Anwenderin \"([^\"]*)\" den Warteraum mit O betreten haben$")
    fun wird_die_Anwenderin_Maria_den_Warteraum_mit_O_betreten_haben(anwender: String) {

        welt {
            tatsachen bestätigen SpielerHatWarteraumBetreten(
                anwender,
                Spieler('O', anwender)
            )
        }
    }

    @Dann("^werde ich den Warteraum als Spieler mit (X|O) betreten haben$")
    fun werde_ich_den_Warteraum_als_Spieler_mit_X_betreten_haben(
        @Transform(SpielerConverter::class) spieler: Spieler
    ) {
        welt {
            tatsachen bestätigen SpielerHatWarteraumBetreten(
                ich.name,
                Spieler(spieler.zeichen, ich.name)
            )
        }
    }

    @Angenommen("^ich habe den Warteraum als Spieler mit dem Zeichen X betreten$")
    fun ich_habe_den_Warteraum_als_Spieler_mit_dem_Zeichen_X_betreten() {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(ich.name, Spieler('X', ich.name)))
        }
    }

    @Wenn("^Der Anwender \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun der_Anwender_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(
        anwender: String
    ) {
        welt.next {
            anwenderverzeichnis.send(WaehleZeichenAus(anwender, Spieler('O', anwender)))
        }
    }

    @Dann("^werde ich mit \"([^\"]*)\" einen Spielpartner gefunden haben$")
    fun werde_ich_mit_einen_Spielpartner_gefunden_haben(anwender: String) {
        welt {
            tatsachen bestätigen SpielpartnerGefunden(
                Spieler('X', ich.name),
                Spieler('O', anwender)
            )
        }
    }
}