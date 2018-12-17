package com.github.haschi.tictactoe.requirements.backend.testing

import cucumber.api.java.Before
import org.assertj.core.api.Assertions.assertThat
import org.springframework.core.env.Environment

class Hooks(private val environment: Environment) {
    @Before
    fun reset() {
        assertThat(environment.activeProfiles)
            .contains("backend", "axon")
    }
}