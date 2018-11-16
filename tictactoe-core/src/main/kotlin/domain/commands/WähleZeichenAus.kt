package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Zeichen
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class WähleZeichenAus(
    @TargetAggregateIdentifier val anwender: String,
    val zeichen: Zeichen
)
