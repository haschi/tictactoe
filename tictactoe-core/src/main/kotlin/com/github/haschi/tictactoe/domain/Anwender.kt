package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import com.github.haschi.tictactoe.domain.values.Zeichen
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

// @Aggregate(commandTargetResolver = "metaDataCommandTargetResolver")
@Aggregate
class Anwender() {
    @AggregateIdentifier
    private lateinit var id: String

    private var zeichen: Zeichen = Zeichen.Keins

    constructor(name: String) : this() {
        AggregateLifecycle.apply(AnwenderRegistriert(name))
    }

    @CommandHandler
    fun verarbeite(command: WähleZeichenAus) {
        if (zeichen != Zeichen.Keins) {
            throw IllegalStateException()
        }

        AggregateLifecycle.apply(ZeichenAusgewählt(id, command.spieler))
    }

    @EventSourcingHandler
    fun falls(event: AnwenderRegistriert) {
        id = event.name
    }

    @EventSourcingHandler
    fun falls(event: ZeichenAusgewählt) {
        zeichen = Zeichen(event.spieler.zeichen)
    }
}