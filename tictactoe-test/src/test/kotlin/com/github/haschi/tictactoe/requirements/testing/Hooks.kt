package com.github.haschi.tictactoe.requirements.testing

import cucumber.api.java.Before

class Hooks(private val welt: DieWelt)
{
    @Before
    fun reset()
    {
        println("RESET")
        welt.reset()
    }
}