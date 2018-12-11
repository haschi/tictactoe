package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RegistriereAnwender(
    @TargetAggregateIdentifier val id: Aggregatkennung,
    val name: String
)
