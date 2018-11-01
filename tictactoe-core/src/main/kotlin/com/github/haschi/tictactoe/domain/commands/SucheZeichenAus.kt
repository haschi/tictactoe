package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Zeichen
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class SucheZeichenAus(
    @TargetAggregateIdentifier val spielerId: Aggregatkennung,
    val zeichen: Zeichen
)