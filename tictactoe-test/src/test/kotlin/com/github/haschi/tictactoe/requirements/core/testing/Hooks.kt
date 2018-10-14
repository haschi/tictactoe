package com.github.haschi.tictactoe.requirements.core.testing

import cucumber.api.java.Before

class Hooks(private val welt: DieWelt) {
    @Before
    fun reset() {
        welt.reset()
    }
}