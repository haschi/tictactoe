package com.github.haschi.tictactoe.requirements.shared.testing

import com.github.haschi.tictactoe.domain.values.Spieler

interface IZustand {
    val spieler: Map<Char, Spieler>
}