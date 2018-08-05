package com.github.haschi.tictactoe.domain.testing

import cucumber.api.java.Before

class Hooks(private val welt: DieWelt) {
    @Before
    fun reset() {
        welt.reset()
    }
}