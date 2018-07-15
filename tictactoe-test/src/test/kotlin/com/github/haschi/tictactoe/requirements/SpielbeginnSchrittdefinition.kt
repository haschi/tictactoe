package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.requirements.testing.SpringSchrittdefinition
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.springframework.stereotype.Component

class SpielbeginnSchrittdefinition : SpringSchrittdefinition()
{
    @Wenn("^ich einer neuen Spielpartie betrete$")
    fun ich_einer_neuen_Spielpartie_betrete()
    {
    }

    @Dann("^werde ich auf einen Gegenspieler warten$")
    fun werde_ich_auf_einen_Gegenspieler_warten()
    {
    }
}