package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Spieler

data class FeldBelegt(
    val spieler: Spieler
) : Exception("Feld belegt")