package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Zeichen
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class WähleZeichenAus(
    @TargetAggregateIdentifier val id: Aggregatkennung,
    val anwender: String,
    val zeichen: Zeichen
)
