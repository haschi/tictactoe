package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.events.AnwenderNichtGefunden
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.AnwenderverzeichnisAngelegt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.AnwenderÜbersicht
import com.github.haschi.tictactoe.domain.values.WarteraumEingerichtet
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.Person
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import mu.KLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat

class AnwenderverzeichnisSteps(private val welt: DieWelt) {


    @Wenn("^ich das Anwenderverzeichnis anlege$")
    fun ich_das_Anwenderverzeichnis_anlege() {
        welt.step {
            welt.anwenderverzeichnis.send(LegeAnwenderverzeichnisAn(Aggregatkennung()))
                .thenApply { anwenderverzeichnisId ->
                    copy(anwenderverzeichnisId = anwenderverzeichnisId,
                        warteraumId = welt.ereignisse.filterIsInstance(AnwenderverzeichnisAngelegt::class.java)
                            .single { it.id == anwenderverzeichnisId }
                            .warteraumId)
                }
        }
    }


    @Dann("^wird das Anwenderverzeichnis keine Anwender enthalten$")
    fun wird_das_Anwenderverzeichnis_keine_Anwender_enthalten() {
        welt.join {
            val antwort = welt.ask(WelcheAnwenderSindBekannt, AnwenderÜbersicht::class.java)

            antwort.join()

            assertThat(antwort)
                .isCompletedWithValue(AnwenderÜbersicht.Leer)
        }
    }

    @Angenommen("^ich habe das Anwenderverzeichnis angelegt$")
    fun ich_habe_das_Anwenderverzeichnis_angelegt() {
        welt.step {
            welt.anwenderverzeichnis.send(LegeAnwenderverzeichnisAn(Aggregatkennung()))
                .thenApply { anwenderverzeichnisId ->
                    copy(anwenderverzeichnisId = anwenderverzeichnisId,
                        warteraumId = welt.ereignisse.filterIsInstance(AnwenderverzeichnisAngelegt::class.java)
                            .single { it.id == anwenderverzeichnisId }
                            .warteraumId)
                }
        }
    }

    @Wenn("^ich mich als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_als_Anwender_registriere(name: String) {
        logger.debug { "Wenn ich mich als Anwender $name registriere" }

        welt.step {
            welt.anwenderverzeichnis.send(RegistriereAnwender(anwenderverzeichnisId, name))
                .exceptionally {
                    logger.error { "Fehler beim Registrieren des Anwenders $name: ${it.message}" }
                    null
                }
                .thenApply { this }
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis nicht gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_nicht_gefunden_haben(arg1: String) {
        logger.debug { "Dann werde ich den Anwender $arg1 im Anwenderverzeichnis nicht gefunden haben" }
        welt.join {
            Assertions.assertThat(ereignisse)
                .describedAs(fehler?.message)
                .contains(AnwenderNichtGefunden(arg1))

            fehler?.apply { println(this) }
        }
    }

    @Angenommen("^ich habe mich als Anwender \"([^\"]*)\" registriert$")
    fun ich_habe_mich_als_Anwender_registriert(arg1: String) {
        logger.debug { "Angenommen ich habe mich als Anwender $arg1 registriert" }

        welt.step {
            welt.anwenderverzeichnis.send(
                RegistriereAnwender(anwenderverzeichnisId, arg1)
            ).thenApply { copy(ich = Person(arg1, Aggregatkennung.NIL)) }
        }
    }

    @Angenommen("^\"([^\"]*)\" hat sich als Anwender registriert$")
    fun hat_sich_als_Anwender_registriert(anwender: String) {
        welt.step {
            welt.anwenderverzeichnis.send(
                RegistriereAnwender(anwenderverzeichnisId, anwender)
            ).thenApply { this }
        }
    }

    @Wenn("^ich mich erneut als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_erneut_als_Anwender_registriere(name: String) {
        logger.debug { "Wenn ich mich erneut als Anwender $name registriere" }
        welt.step {
            welt.anwenderverzeichnis.send(
                RegistriereAnwender(anwenderverzeichnisId, name)
            ).thenApply { this }
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_gefunden_haben(name: String) {
        logger.debug { "Dann werde ich den Anwender $name im Anwenderverzeichnis gefunden haben" }
        welt.join {
            Assertions.assertThat(ereignisse)
                .describedAs(fehler?.localizedMessage)
                .containsOnlyOnce(AnwenderRegistriert(name, zustand.warteraumId))
        }
    }

    @Dann("werde ich einen Warteraum dazu eingerichtet haben")
    fun `Dann werde ich einen Warteraum dazu einrichten`() {
        welt.join {

            assertThat(fehler)
                .describedAs(fehler?.message)
                .isNull()

            assertThat(ereignisse)
                .containsOnlyOnce(WarteraumEingerichtet(zustand.warteraumId))
        }
    }

    companion object : KLogging()
}