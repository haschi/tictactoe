package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielzug

data class SetzeZeichen(
    val spielId: Aggregatkennung,
    val spielzug: Spielzug
)
