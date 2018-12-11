package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class SetzeZeichen(
    @TargetAggregateIdentifier val spielId: Aggregatkennung,
    val spielzug: Spielzug
)
