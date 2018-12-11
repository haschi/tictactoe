package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielzug

data class SpielzugWurdeAkzeptiert(
    val spielId: Aggregatkennung,
    val spielzug: Spielzug
)
