package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import cucumber.api.java.de.Dann
import org.assertj.core.api.Assertions.assertThat


class AnwenderSteps(private val welt: DieWelt) {

    @Dann("^werde ich eine Fehlermeldung erhalten$")
    fun `Dann werde ich eine Fehlermeldung erhalten`() {
        assertThat(welt.future).isCompletedExceptionally
    }
}