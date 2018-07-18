package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.events.SpielBegonnen
import com.github.haschi.tictactoe.domain.events.SpielerNichtAndDerReiheGewesen
import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class TicTacToe
{
    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    private var besetzteFelder = listOf<Feld>()
    private var letzterSpieler = Spieler.keiner

    constructor()

    @CommandHandler
    constructor(command: BeginneSpiel)
    {
        AggregateLifecycle.apply(SpielBegonnen(command.id))
    }

    @CommandHandler
    fun setzeZeichen(command: SetzeZeichen)
    {
        if (besetzteFelder.contains(command.feld))
        {
            throw FeldBelegt(id, command.spieler)
        }

        if (letzterSpieler == command.spieler)
        {
            throw SpielerNichtAndDerReiheGewesen(id, command.spieler)
        }

        AggregateLifecycle.apply(
                SpielzugWurdeAkzeptiert(id, command.spieler, command.feld))
    }

    @EventSourcingHandler
    fun falls(event: SpielBegonnen)
    {
        id = event.id
    }

    @EventSourcingHandler
    fun falls(event: SpielzugWurdeAkzeptiert)
    {
        besetzteFelder += event.feld
        letzterSpieler = event.spieler
    }
}