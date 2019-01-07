package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielBegonnen(
    val id: Aggregatkennung,
    val x: Spieler,
    val o: Spieler
)
