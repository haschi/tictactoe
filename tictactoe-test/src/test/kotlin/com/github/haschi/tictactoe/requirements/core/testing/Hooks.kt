package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.DatingRoom
import com.github.haschi.tictactoe.domain.commands.RichteDatingRoomEin
import cucumber.api.java.Before

class Hooks(private val welt: DieWelt) {
    @Before
    fun reset() {
        welt.reset()
        welt.next {
            anwenderverzeichnis.send(RichteDatingRoomEin(DatingRoom.ID))
        }
    }
}