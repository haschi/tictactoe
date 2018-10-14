package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Anwender() {
    @AggregateIdentifier
    lateinit var id: String

    constructor(name: String) : this() {
        AggregateLifecycle.apply(AnwenderRegistriert(name))
    }

    @CommandHandler
    fun verarbeite(command: WaehleZeichenAus) {
        AggregateLifecycle.apply(ZeichenAusgewählt(id, command.spieler))
    }

    @EventSourcingHandler
    fun falls(event: AnwenderRegistriert) {
        id = event.name
    }
}