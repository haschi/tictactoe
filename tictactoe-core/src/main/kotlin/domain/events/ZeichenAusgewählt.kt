package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Spieler

data class ZeichenAusgewählt(val id: String, val spieler: Spieler)
