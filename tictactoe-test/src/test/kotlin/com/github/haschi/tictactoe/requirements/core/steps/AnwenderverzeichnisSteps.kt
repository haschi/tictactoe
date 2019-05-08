package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.Anwendereigenschaften
import com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt
import com.github.haschi.tictactoe.domain.WelcheEigenschaftenBesitztAnwender
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.events.AnwenderNichtGefunden
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.AnwenderÜbersicht
import com.github.haschi.tictactoe.domain.values.WarteraumEingerichtet
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.core.testing.Person
import com.github.haschi.tictactoe.requirements.shared.testing.Resolver
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Und
import cucumber.api.java.de.Wenn
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import mu.KLogging
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat

class AnwenderverzeichnisSteps(private val welt: DieWelt) {

    @Wenn("^ich das Anwenderverzeichnis anlege$")
    fun ich_das_Anwenderverzeichnis_anlege() {
        welt.compose {
            welt.anwenderverzeichnis.send(LegeAnwenderverzeichnisAn(Aggregatkennung()))
                .thenApply { anwenderverzeichnisId ->
                    copy(anwenderverzeichnisId = anwenderverzeichnisId)
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
        welt.compose {
            welt.anwenderverzeichnis.send(LegeAnwenderverzeichnisAn(Aggregatkennung()))
                .thenApply { anwenderverzeichnisId ->
                    copy(anwenderverzeichnisId = anwenderverzeichnisId)
                }
        }
    }

    @Wenn("^ich mich als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_als_Anwender_registriere(name: String) {
        logger.debug { "Wenn ich mich als Anwender $name registriere" }

        welt.compose {
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
            Assertions.assertThat(zustand.ereignisse)
                .describedAs(fehler?.message)
                .contains(AnwenderNichtGefunden(arg1, this.zustand.anwender.getValue(arg1).id))
        }
    }

    @Angenommen("^ich habe mich als Anwender \"([^\"]*)\" registriert$")
    fun ich_habe_mich_als_Anwender_registriert(name: String) {
        logger.debug { "Angenommen ich habe mich als Anwender $name registriert" }

        welt.compose {
            welt.anwenderverzeichnis.send(
                RegistriereAnwender(anwenderverzeichnisId, name)
            ).thenApply { anwenderId ->
                copy(
                    ich = Person(name, anwenderId),
                    anwender = anwender + (name to Person(name, anwenderId))
                )
            }
        }
    }

    @Angenommen("^\"([^\"]*)\" hat sich als Anwender registriert$")
    fun hat_sich_als_Anwender_registriert(name: String) {
        welt.compose {
            welt.anwenderverzeichnis.send(RegistriereAnwender(anwenderverzeichnisId, name))
                .thenApply { anwenderId ->
                    copy(anwender = anwender + (name to Person(name, anwenderId)))
                }
        }
    }

    @Wenn("^ich mich erneut als Anwender \"([^\"]*)\" registriere$")
    fun ich_mich_erneut_als_Anwender_registriere(name: String) {
        logger.debug { "Wenn ich mich erneut als Anwender $name registriere" }
        welt.compose {
            welt.anwenderverzeichnis.send(
                RegistriereAnwender(anwenderverzeichnisId, name)
            ).thenApply { this }
        }
    }

    @Dann("^werde ich den Anwender \"([^\"]*)\" im Anwenderverzeichnis gefunden haben$")
    fun werde_ich_den_Anwender_im_Anwenderverzeichnis_gefunden_haben(name: String) {
        logger.debug { "Dann werde ich den Anwender $name im Anwenderverzeichnis gefunden haben" }
        welt.join {
            val person: Person = zustand.anwender.getValue(name)
            Assertions.assertThat(zustand.ereignisse)
                .describedAs(fehler?.localizedMessage)
                .containsOnlyOnce(AnwenderRegistriert(person.id, person.name, zustand.warteraumId))
        }
    }

    @Dann("werde ich einen Warteraum dazu eingerichtet haben")
    fun `Dann werde ich einen Warteraum dazu einrichten`() {
        welt.join {

            assertThat(fehler)
                .describedAs(fehler?.message)
                .isNull()

            assertThat(zustand.ereignisse)
                .containsOnlyOnce(WarteraumEingerichtet(zustand.warteraumId))
        }
    }

    @Dann("werde ich das Anwenderverzeichnis in der Übersicht sehen")
    fun `Dann werde ich das Anwenderverzeichnis in der Übersicht sehen`() {
        welt.versuche { zustand ->

            val antwort = welt.ask(
                WelcheAnwenderverzeichnisseGibtEs,
                AnwenderverzeichnisÜbersicht::class.java
            )
                .join()

            assertThat(antwort)
                .isEqualTo(AnwenderverzeichnisÜbersicht(zustand.anwenderverzeichnisId))
        }
    }

    @Und("ich werde die vom Profilersteller erstellten Eigenschaften von \"{anwender}\" abrufen können")
    fun `Und ich werde die vom Profiler erstellten Eigenschaften von Anwender abrufen können`(anwender: Resolver<Person>) {
        welt.versuche { zustand ->
            val antwort = welt.ask(
                WelcheEigenschaftenBesitztAnwender(anwender.resolve(zustand).id),
                Anwendereigenschaften::class.java
            )
                .join()
            assertThat(antwort)
                .isEqualTo(Anwendereigenschaften(anwender.resolve(zustand).name))
        }
    }

    companion object : KLogging()
}