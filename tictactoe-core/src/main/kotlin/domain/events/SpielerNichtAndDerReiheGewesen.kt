package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielerNichtAndDerReiheGewesen(
    val spieler: Spieler
) : Exception("Spieler ist nicht an der Reihe")
