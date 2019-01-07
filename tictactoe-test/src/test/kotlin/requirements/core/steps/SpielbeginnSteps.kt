package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.SpielBegonnen
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.TestApplication
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(classes = [TestApplication::class])
@DirtiesContext
class SpielbeginnSteps(val welt: DieWelt) {

    @Angenommen("^ich bin der Anwender \"([^\"]*)\"$")
    fun ich_bin_der_Anwender(name: String) {
        welt.compose {
            welt.tictactoe.send(RegistriereAnwender(anwenderverzeichnisId, name))
                .thenApply { this }
        }
    }

    @Angenommen("ich habe das Symbol \"{zeichen}\" ausgewählt")
    fun `ich habe das Symbol ausgewählt`(zeichen: Zeichen) {
        welt.compose {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.id, ich.name, zeichen))
                .thenApply { this }
        }
    }

    @Wenn("wenn der Anwender \"{name}\" das Symbol \"{zeichen}\" auswählt")
    fun `Wenn der Anwender das Symbol auswählt`(name: String, zeichen: Zeichen) {
        welt.compose {
            welt.anwenderverzeichnis.send(WähleZeichenAus(this.anwender[name]!!.id, name, zeichen))
                .thenApply { this }
        }
    }

    @Dann("^beginne ich mit dem Anwender \"([^\"]*)\" eine Partie Tic Tac Toe$")
    fun beginne_ich_mit_dem_Anwender_eine_Partie_Tic_Tac_Toe(arg1: String) {
        welt.join {
            assertThat(zustand.ereignisse)
                .containsOnlyOnce(
                    SpielBegonnen(
                        zustand.spielId,
                        Spieler('X', zustand.ich.name),
                        Spieler('O', zustand.anwender[arg1]!!.name)
                    )
                )
        }
    }
}