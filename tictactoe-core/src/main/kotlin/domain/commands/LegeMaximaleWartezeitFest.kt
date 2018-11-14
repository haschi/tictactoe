package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.time.Duration

data class LegeMaximaleWartezeitFest(
    @TargetAggregateIdentifier val id: Aggregatkennung,
    val wartezeit: Duration
)