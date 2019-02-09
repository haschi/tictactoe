package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.WähleZeichenAus
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Zeichen
import domain.commands.KehreVomSpielZurück
import domain.events.VomSpielZurückgekehrt
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Anwender() {
    @AggregateIdentifier
    private lateinit var id: Aggregatkennung
    private lateinit var name: String

    private lateinit var zugewiesenerWarteraum: Aggregatkennung
    private var zeichen: Zeichen = Zeichen.Keins

    constructor(
        id: Aggregatkennung,
        name: String,
        zugewiesenerWarteraum: Aggregatkennung
    ) : this() {
        AggregateLifecycle.apply(AnwenderRegistriert(id, name, zugewiesenerWarteraum))
    }

    @CommandHandler
    @Throws(AuswahlNichtMöglich::class)
    fun bearbeite(command: WähleZeichenAus) {
        if (zeichen != Zeichen.Keins) {
            val meldung = "Du hast bereits das Zeichen ${zeichen.wert} ausgewählt"
            throw AuswahlNichtMöglich(meldung)
        }

        AggregateLifecycle.apply(
            ZeichenAusgewählt(
                name,
                Spieler(command.zeichen.wert, command.anwender, command.id),
                zugewiesenerWarteraum
            )
        )
    }

    @CommandHandler
    fun bearbeite(command: KehreVomSpielZurück) {
        AggregateLifecycle.apply(VomSpielZurückgekehrt(command.spieler))
    }

    @EventSourcingHandler
    fun falls(event: AnwenderRegistriert) {
        id = event.id
        name = event.name
        zugewiesenerWarteraum = event.zugewiesenerWarteraum
    }

    @EventSourcingHandler
    fun falls(event: ZeichenAusgewählt) {
        zeichen = Zeichen(event.spieler.zeichen)
    }
}