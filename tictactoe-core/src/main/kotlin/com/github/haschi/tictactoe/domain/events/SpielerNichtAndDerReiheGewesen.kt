package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielerNichtAndDerReiheGewesen(
        val spielId: Aggregatkennung,
        val spieler: Spieler): Exception()
