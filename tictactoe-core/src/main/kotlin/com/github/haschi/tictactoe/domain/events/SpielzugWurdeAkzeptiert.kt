package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielzugWurdeAkzeptiert(
        val spielId: Aggregatkennung,
        val spieler: Spieler,
        val feld: Feld)
