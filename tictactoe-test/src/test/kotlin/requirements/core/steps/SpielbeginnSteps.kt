package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.values.Zeichen
import com.github.haschi.tictactoe.requirements.core.TestApplication
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.PendingException
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(classes = [TestApplication::class])
@DirtiesContext
class SpielbeginnSteps(val welt: DieWelt) {

    @Angenommen("^ich bin der Anwender \"([^\"]*)\"$")
    fun ich_bin_der_Anwender(name: String) {
        welt.step {
            welt.tictactoe.send(RegistriereAnwender(anwenderverzeichnisId, name))
                .thenApply { this }
        }
    }

    @Angenommen("ich habe das Symbol \"{zeichen}\" ausgewählt")
    fun `ich habe das Symbol ausgewählt`(zeichen: Zeichen) {
        welt.step {
            welt.anwenderverzeichnis.send(WähleZeichenAus(ich.id, ich.name, zeichen))
                .thenApply { this }
        }
    }

    @Wenn("^wenn der Anwender \"([^\"]*)\" das Symbol\"([^\"]*)\" auswählt$")
    fun `Wenn der Anwender das Symbol auswählt`(arg1: String, arg2: String) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Dann("^beginne ich mit dem Anwender \"([^\"]*)\" eine Partie Tic Tac Toe$")
    fun beginne_ich_mit_dem_Anwender_eine_Partie_Tic_Tac_Toe(arg1: String) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }
}