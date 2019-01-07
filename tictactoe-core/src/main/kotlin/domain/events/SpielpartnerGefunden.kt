package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielpartnerGefunden(val x: Spieler, val o: Spieler)
