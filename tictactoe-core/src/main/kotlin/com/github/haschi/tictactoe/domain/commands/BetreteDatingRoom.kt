package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class BetreteDatingRoom(
    @TargetAggregateIdentifier val id: Aggregatkennung,
    val spielerId: String,
    val spieler: Spieler
)
