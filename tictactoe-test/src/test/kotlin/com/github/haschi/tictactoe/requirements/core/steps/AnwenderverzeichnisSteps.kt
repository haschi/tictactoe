package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt
import com.github.haschi.tictactoe.domain.commands.AnwenderNichtGefunden
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.values.Anwenderübersicht
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory


class AnwenderverzeichnisSteps(private val welt: DieWelt) {


    @Wenn("^ich das Anwenderverzeichnis anlege$")
    fun ich_das_Anwenderverzeichnis_anlege() {
        welt.next {
            anwenderverzeichnis.send(
                LegeAnwenderverzeichnisAn(LegeAnwenderverzeichnisAn.ID),
                LegeAnwenderverzeichnisAn.ID.toString()
            )
        }
    }

    @Dann("^wird das Anwenderverzeichnis keine Anwender enthalten$")
    fun wird_das_Anwenderverzeichnis_keine_Anwender_enthalten() {
        welt.next {
            ask(WelcheAnwenderSindBekannt, Anwenderübersicht::class.java)
        }

        assertThat(welt.future)
            .isCompletedWithValue(Anwenderübersicht.Leer)
    }

    @Angenommen("^ich habe das Anwenderverzeichnis angelegt$")
    fun ich_habe_das_Anwenderverzeichnis_angelegt() {

        welt.next {
            anwenderverzeichnis.send(
                LegeAnwenderverzeichnisAn(LegeAnwenderverzeichnisAn.ID),
                LegeAnwenderverzeichnisAn.ID.toString()
            )
        }
    }

    @Wenn("^ich mich als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_als_Anwender_registriere(arg1: String) {
        Logger.debug { "Wenn ich mich als Anwender $arg1 registriere" }

        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(LegeAnwenderverzeichnisAn.ID, arg1),
                LegeAnwenderverzeichnisAn.ID.toString()
            )
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis nicht gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_nicht_gefunden_haben(arg1: String) {
        Logger.debug { "Dann werde ich den Anwender $arg1 im Anwenderverzeichnis nicht gefunden haben" }
        welt.future.get()
        Assertions.assertThat(welt.events)
            .contains(AnwenderNichtGefunden(arg1))
    }

    @Angenommen("^ich habe mich als Anwender \"([^\"]*)\" registriert$")
    fun ich_habe_mich_als_Anwender_registriert(arg1: String) {
        Logger.debug { "Angenommen ich habe mich als Anwender $arg1 registriert" }
        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(LegeAnwenderverzeichnisAn.ID, arg1),
                LegeAnwenderverzeichnisAn.ID.toString()
            )
        }
    }

    @Wenn("^ich mich erneut als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_erneut_als_Anwender_registriere(arg1: String) {
        Logger.debug { "Wenn ich mich erneut als Anwender $arg1 registriere" }
        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(LegeAnwenderverzeichnisAn.ID, arg1),
                LegeAnwenderverzeichnisAn.ID.toString()
            )
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_gefunden_haben(arg1: String) {
        Logger.debug { "Dann werde ich den Anwender $arg1 im Anwenderverzeichnis gefunden haben" }
        welt.future.get()
        Assertions.assertThat(welt.events)
            .containsOnlyOnce(AnwenderRegistriert(arg1))
    }

    companion object {
        val Logger: Logger = LoggerFactory.getLogger(AnwenderverzeichnisSteps::class.java)!!
    }
}