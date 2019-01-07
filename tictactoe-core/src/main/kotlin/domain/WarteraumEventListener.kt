package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler

import org.springframework.stereotype.Service

@Service
class WarteraumEventListener(val tictactoe: TicTacToeGateway) {
    @EventHandler
    fun falls(event: SpielpartnerGefunden) {
        println(event)
        tictactoe.send(
            BeginneSpiel(
                Aggregatkennung(),
                event.x,
                event.o
            )
        )
        // TODO: Fehlerbehandlung
    }
}