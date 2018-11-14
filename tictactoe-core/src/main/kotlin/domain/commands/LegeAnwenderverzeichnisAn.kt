package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class LegeAnwenderverzeichnisAn(
    @TargetAggregateIdentifier val id: Aggregatkennung
)