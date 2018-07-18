package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class SetzeZeichen(
        @TargetAggregateIdentifier val spielId: Aggregatkennung,
        val spieler: Spieler,
        val feld: Feld)
