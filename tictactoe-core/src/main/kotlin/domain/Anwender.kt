package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Anwender() {
    @AggregateIdentifier
    private lateinit var id: String

    private lateinit var zugewiesenerWarteraum: Aggregatkennung
    private var zeichen: Zeichen = Zeichen.Keins

    constructor(name: String, zugewiesenerWarteraum: Aggregatkennung) : this() {
        AggregateLifecycle.apply(AnwenderRegistriert(name, zugewiesenerWarteraum))
    }

    @CommandHandler
    fun bearbeite(command: WähleZeichenAus) {
        if (zeichen != Zeichen.Keins) {
            val meldung = "Du hast bereits das Zeichen ${zeichen.wert} ausgewählt"
            throw AuswahlNichtMöglich(meldung)
        }

        AggregateLifecycle.apply(
            ZeichenAusgewählt(
                id,
                Spieler(command.zeichen.wert, command.anwender),
                zugewiesenerWarteraum
            )
        )
    }

    @EventSourcingHandler
    fun falls(event: AnwenderRegistriert) {
        id = event.name
        zugewiesenerWarteraum = event.zugewiesenerWarteraum
    }

    @EventSourcingHandler
    fun falls(event: ZeichenAusgewählt) {
        zeichen = Zeichen(event.spieler.zeichen)
    }
}