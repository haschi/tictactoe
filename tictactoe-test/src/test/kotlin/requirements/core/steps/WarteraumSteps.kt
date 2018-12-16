package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.LegeMaximaleWartezeitFest
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.MaximaleWartezeitFestgelegt
import com.github.haschi.tictactoe.domain.events.SpielerHatWarteraumBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.events.WarteraumVerlassen
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import java.time.Duration

class WarteraumSteps(private val welt: DieWelt) {

    @Angenommen("ich habe eine maximale Wartezeit von {zeitraum} für den Warteraum festgelegt")
    fun `Abgenommen ich habe eine maximale Wartezeit für den Warteraum festgelegt`(wartezeit: Duration) {
        welt.step {
            welt.warteraum.send(
                LegeMaximaleWartezeitFest(warteraumId, wartezeit)
            ).thenApply { this }
        }
    }

    @Angenommen("die Anwender Martin und Matthias haben sich als Spielpartner gefunden")
    fun die_Anwender_Martin_und_Matthias_haben_sich_als_Spielpartner_gefunden() {
        welt.step { welt.anwenderverzeichnis.send(WähleZeichenAus("Matthias", Zeichen.X)).thenApply { this } }
        welt.step { welt.anwenderverzeichnis.send(WähleZeichenAus("Martin", Zeichen.O)).thenApply { this } }
    }

    @Angenommen("ich habe X als mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht")
    fun `Angenommen ich habe mein Zeichen für die nächste Partie Tic Tac Toe ausgesucht`() {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.name, Zeichen.X))
                .thenApply { this }
        }
    }

    @Wenn("ich die maximale Wartezeit überschritten habe")
    fun `Wenn ich die maximale Wartezeit überschritten habe`() {
        welt.join {
            val maximaleWartezeit =
                ereignisse.filterIsInstance(MaximaleWartezeitFestgelegt::class.java)
                    .map { it.wartezeit.toMillis() }
                    .last()

            Thread.sleep(maximaleWartezeit + 100)
        }
    }

    @Dann("werde ich den Warteraum ohne Spielpartner verlassen haben")
    fun werde_ich_den_Warteraum_ohne_Spielpartner_verlassen_haben() {
        welt.join {
            assertThat(ereignisse).contains(WarteraumVerlassen(zustand.ich.name))
        }
    }

    @Wenn("^Die Anwenderin \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun die_Anwenderin_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(anwender: String) {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(anwender, Zeichen.O))
                .thenApply { this }
        }
    }

    @Dann("^wird die Anwenderin \"([^\"]*)\" den Warteraum mit O betreten haben$")
    fun wird_die_Anwenderin_Maria_den_Warteraum_mit_O_betreten_haben(anwender: String) {

        welt.tatsachen bestätigen SpielerHatWarteraumBetreten(
                Spieler('O', anwender)
            )
    }

    @Dann("werde ich den Warteraum als Spieler mit {spieler} betreten haben")
    fun werde_ich_den_Warteraum_als_Spieler_mit_X_betreten_haben(spieler: Spieler) {
        welt.join {
            assertThat(ereignisse).contains(
                SpielerHatWarteraumBetreten(
                    Spieler(spieler.zeichen, zustand.ich.name)
                )
            )
        }
    }

    @Angenommen("ich habe den Warteraum als Spieler mit dem Zeichen X betreten")
    fun ich_habe_den_Warteraum_als_Spieler_mit_dem_Zeichen_X_betreten() {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.name, Zeichen.X))
                .thenApply { this }
        }
    }

    @Wenn("^Der Anwender \"([^\"]*)\" den Warteraum als Spieler mit dem Zeichen O betritt$")
    fun der_Anwender_den_Warteraum_als_Spieler_mit_dem_Zeichen_O_betritt(
        anwender: String
    ) {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(anwender, Zeichen.O))
                .thenApply { this }
        }
    }

    @Dann("^werde ich mit \"([^\"]*)\" einen Spielpartner gefunden haben$")
    fun werde_ich_mit_einen_Spielpartner_gefunden_haben(anwender: String) {
        welt.join {
            assertThat(ereignisse).contains(
                SpielpartnerGefunden(
                    Spieler('X', zustand.ich.name),
                    Spieler('O', anwender)
                )
            )
        }
    }
}