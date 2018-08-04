package com.github.haschi.tictactoe.domain.steps

import com.github.haschi.tictactoe.TestApplication
import com.github.haschi.tictactoe.domain.testing.DieWelt
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TestApplication::class])
class SpielbeginnSteps(val welt: DieWelt) {
    @Wenn("^ich einer neuen Tic Tac Toe Partie betrete$")
    fun `ich einer neuen Tic Tac Toe Partie betrete`() {
        welt.spielername = "Ich"
    }

    @Dann("^werde ich auf einen Gegenspieler warten$")
    fun `werde ich auf einen Gegenspieler_warten`() {
        assertThat(welt.spielername).isEqualTo("Ich")
    }
}