package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielerParameter(
    val x: Spieler,
    val o: Spieler
)
