package com.github.haschi.tictactoe.requirements.shared.testing

import com.github.haschi.tictactoe.domain.values.Spielzug
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt

interface SpielzugGenerator {
    fun auflösen(zustand: DieWelt.Zustand): Spielzug
}
