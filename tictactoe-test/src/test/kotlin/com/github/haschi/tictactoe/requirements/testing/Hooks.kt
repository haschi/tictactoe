package com.github.haschi.tictactoe.requirements.testing

import cucumber.api.java.Before
import org.springframework.stereotype.Component


class Hooks(val welt: DieWelt)
{
    @Before
    fun reset()
    {
        println("RESET")
        welt.reset()
    }
}