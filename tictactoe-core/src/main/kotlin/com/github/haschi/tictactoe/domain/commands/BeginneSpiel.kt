package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class BeginneSpiel(
    @TargetAggregateIdentifier val id: Aggregatkennung
)
