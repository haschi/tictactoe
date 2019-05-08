package com.github.haschi.tictactoe.requirements.shared.testing

import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.core.testing.Person

interface IZustand {
    val spieler: Map<Char, Spieler>
    val anwender: Map<String, Person>
}