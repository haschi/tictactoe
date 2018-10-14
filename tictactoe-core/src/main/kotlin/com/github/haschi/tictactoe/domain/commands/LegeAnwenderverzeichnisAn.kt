package com.github.haschi.tictactoe.domain.commands

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.net.URI

data class LegeAnwenderverzeichnisAn(@TargetAggregateIdentifier val id: Aggregatkennung) {
    companion object {
        val ID = Aggregatkennung(URI("singleton", "Anwenderverzeichnis", ""))
    }
}