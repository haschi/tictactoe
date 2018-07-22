package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.TestApplication
import com.github.haschi.tictactoe.requirements.testing.DieWelt
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
class SpielbeginnSchrittdefinition(val welt: DieWelt)
{
    @Wenn("^ich einer neuen Tic Tac Toe Partie betrete$")
    fun ich_einer_neuen_tictactoe_partie_betrete()
    {
        welt.spielername = "Ich"
    }

    @Dann("^werde ich auf einen Gegenspieler warten$")
    fun werde_ich_auf_einen_Gegenspieler_warten()
    {
        assertThat(welt.spielername).isEqualTo("Ich")
    }
}