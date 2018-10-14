package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.commandhandling.TargetAggregateIdentifier

data class WaehleZeichenAus(@TargetAggregateIdentifier val anwender: String, val spieler: Spieler)
