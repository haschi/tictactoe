package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.Warteraum
import com.github.haschi.tictactoe.domain.commands.LegeMaximaleWartezeitFest
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.MaximaleWartezeitFestgelegt
import com.github.haschi.tictactoe.domain.events.SpielerHatWarteraumBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.events.WarteraumVerlassen
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.DurationConverter
import com.github.haschi.tictactoe.requirements.core.testing.SpielerConverter
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import java.time.Duration

class WarteraumSteps(private val welt: DieWelt) {

    @Angenommen("^ich habe eine maximale Wartezeit von \"([^\"]*)\" für den Warteraum festgelegt$")
    fun `Abgenommen ich habe eine maximale Wartezeit für den Warteraum festgelegt`(
        @Transform(DurationConverter::class) wartezeit: Duration
    ) {
        welt.next {
            warteraum.send(
                LegeMaximaleWartezeitFest(Warteraum.ID, wartezeit)
            )
        }
    }

    @Angenommen("^die Anwender Martin und Matthias haben sich als Spielpartner gefunden$")
    fun die_Anwender_Martin_und_Matthias_haben_sich_als_Spielpartner_gefunden() {
        welt.schritt(
            { anwenderverzeichnis.send(WähleZeichenAus("Matthias", Spieler('X', "Matthias"), Zeichen.X)) },
            { anwenderverzeichnis.send(WähleZeichenAus("Martin", Spieler('O', "Martin"), Zeichen.O)) }
        )
    }

    @Angenommen("^ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht$")
    fun `Angenommen ich habe mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht`() {
        welt.next {
            anwenderverzeichnis.send(WähleZeichenAus(ich.name, Spieler('X', ich.name), Zeichen.X))
        }
    }

    @Wenn("^ich die maximale Wartezeit überschritten habe$")
    fun `Wenn ich die maximale Wartezeit überschritten habe`() {

        welt {
            future.get()
            val maximaleWartezeit =
                ereignisse.filterIsInstance(MaximaleWartezeitFestgelegt::class.java)
                    .map { it.wartezeit.toMillis() }
                    .last()

            Thread.sleep(maximaleWartezeit + 100)
        }
    }

    @Dann("^werde ich den Warteraum ohne Spielpartner verlassen haben$")
    fun werde_ich_den_Warteraum_ohne_Spielpartner_verlassen_haben() {
        welt {
            tatsachen bestätigen WarteraumVerlassen(ich.name)
        }
    }

    @Wenn("^Die Anwenderin \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun die_Anwenderin_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(anwender: String) {
        welt.next {
            anwenderverzeichnis.send(WähleZeichenAus(anwender, Spieler('O', anwender), Zeichen.O))
        }
    }

    @Dann("^wird die Anwenderin \"([^\"]*)\" den Warteraum mit O betreten haben$")
    fun wird_die_Anwenderin_Maria_den_Warteraum_mit_O_betreten_haben(anwender: String) {

        welt {
            tatsachen bestätigen SpielerHatWarteraumBetreten(
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
                Spieler(spieler.zeichen, ich.name)
            )
        }
    }

    @Angenommen("^ich habe den Warteraum als Spieler mit dem Zeichen X betreten$")
    fun ich_habe_den_Warteraum_als_Spieler_mit_dem_Zeichen_X_betreten() {
        welt.next {
            anwenderverzeichnis.send(WähleZeichenAus(ich.name, Spieler('X', ich.name), Zeichen.X))
        }
    }

    @Wenn("^Der Anwender \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun der_Anwender_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(
        anwender: String
    ) {
        welt.next {
            anwenderverzeichnis.send(WähleZeichenAus(anwender, Spieler('O', anwender), Zeichen.O))
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