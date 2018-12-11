package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.Warteraum
import com.github.haschi.tictactoe.domain.commands.RichteWarteraumEin
import cucumber.api.java.Before

class Hooks(private val welt: DieWelt) {
    @Before
    fun reset() {
        welt.reset()
        welt.next {
            warteraum.send(RichteWarteraumEin(Warteraum.ID))
        }
    }
}