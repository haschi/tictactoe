package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.requirements.testing.DieWelt
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SpielbeginnSchrittdefinition(val welt: DieWelt)
{
    @Wenn("^ich einer neuen Spielpartie betrete$")
    fun ich_einer_neuen_Spielpartie_betrete()
    {
        welt.spielername = "Ich"
    }

    @Dann("^werde ich auf einen Gegenspieler warten$")
    fun werde_ich_auf_einen_Gegenspieler_warten()
    {
        assertThat(welt.spielername).isEqualTo("Ich")
    }
}