package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Spielfeld
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class SpielEventListener {

    var spielfeld = Spielfeld.leer

    @QueryHandler
    fun falls(query: SpielfeldQuery): Spielfeld {
        println(query)
        return spielfeld
    }

    @EventHandler
    fun falls(event: SpielzugWurdeAkzeptiert) {
        println("Event Handler: Spielzug wurde akzeptiert: $event")
        spielfeld = spielfeld.hinzufügen(event.spielzug)
    }
}