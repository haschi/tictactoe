package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.Anwenderverzeichnis
import com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.events.AnwenderNichtGefunden
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.AnwenderÜbersicht
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.Person
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
                LegeAnwenderverzeichnisAn(Anwenderverzeichnis.ID)
            )
        }
    }

    @Dann("^wird das Anwenderverzeichnis keine Anwender enthalten$")
    fun wird_das_Anwenderverzeichnis_keine_Anwender_enthalten() {
        welt.next {
            ask(WelcheAnwenderSindBekannt, AnwenderÜbersicht::class.java)
        }

        assertThat(welt.future)
            .isCompletedWithValue(AnwenderÜbersicht.Leer)
    }

    @Angenommen("^ich habe das Anwenderverzeichnis angelegt$")
    fun ich_habe_das_Anwenderverzeichnis_angelegt() {

        welt.next {
            anwenderverzeichnis.send(
                LegeAnwenderverzeichnisAn(Anwenderverzeichnis.ID)
            )
        }
    }

    @Wenn("^ich mich als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_als_Anwender_registriere(arg1: String) {
        Logger.debug { "Wenn ich mich als Anwender $arg1 registriere" }

        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(Anwenderverzeichnis.ID, arg1)
            )
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis nicht gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_nicht_gefunden_haben(arg1: String) {
        Logger.debug { "Dann werde ich den Anwender $arg1 im Anwenderverzeichnis nicht gefunden haben" }
        welt.future.get()
        Assertions.assertThat(welt.ereignisse)
            .contains(AnwenderNichtGefunden(arg1))
    }

    @Angenommen("^ich habe mich als Anwender \"([^\"]*)\" registriert$")
    fun ich_habe_mich_als_Anwender_registriert(arg1: String) {
        Logger.debug { "Angenommen ich habe mich als Anwender $arg1 registriert" }

        welt.ich = Person(arg1, Aggregatkennung.NIL)

        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(Anwenderverzeichnis.ID, arg1)
            )
        }
    }

    @Angenommen("^\"([^\"]*)\" hat sich als Anwender registriert$")
    fun hat_sich_als_Anwender_registriert(anwender: String) {
        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(Anwenderverzeichnis.ID, anwender)
            )
        }
    }

    @Wenn("^ich mich erneut als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_erneut_als_Anwender_registriere(arg1: String) {
        Logger.debug { "Wenn ich mich erneut als Anwender $arg1 registriere" }
        welt.next {
            anwenderverzeichnis.send(
                RegistriereAnwender(Anwenderverzeichnis.ID, arg1)
            )
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_gefunden_haben(arg1: String) {
        Logger.debug { "Dann werde ich den Anwender $arg1 im Anwenderverzeichnis gefunden haben" }
        welt.future.get()
        Assertions.assertThat(welt.ereignisse)
            .containsOnlyOnce(AnwenderRegistriert(arg1))
    }

    companion object {
        val Logger: Logger = LoggerFactory.getLogger(AnwenderverzeichnisSteps::class.java)!!
    }
}