package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielzugResource(val spieler: Spieler, val feld: Feld)
