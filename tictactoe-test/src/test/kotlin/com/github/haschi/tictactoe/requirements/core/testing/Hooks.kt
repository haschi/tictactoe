package com.github.haschi.tictactoe.requirements.core.testing

import cucumber.api.java.Before
import org.assertj.core.api.Assertions.assertThat
import org.springframework.core.env.Environment

class Hooks(private val welt: DieWelt, private val environment: Environment) {
    @Before
    fun reset() {
        assertThat(environment.activeProfiles)
            .isEmpty()

        welt.reset()
    }
}
