package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class BeginneSpiel(
    @TargetAggregateIdentifier val id: Aggregatkennung,
    val x: Spieler,
    val o: Spieler
)
