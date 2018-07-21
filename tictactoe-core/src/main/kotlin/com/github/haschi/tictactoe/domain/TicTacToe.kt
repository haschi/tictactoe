package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.events.SpielBegonnen
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import com.github.haschi.tictactoe.domain.events.SpielerNichtAndDerReiheGewesen
import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
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

    val besetzteFelder: List<Feld>
        get() = spielverlauf.map { it.feld }

    val letzterSpieler: Spieler
           get() = spielverlauf.map { it.spieler }
                   .lastOrNull() ?: Spieler.keiner

    private var spielverlauf = listOf<Spielzug>()

    constructor()

    @CommandHandler
    constructor(command: BeginneSpiel)
    {
        AggregateLifecycle.apply(SpielBegonnen(command.id))
    }

    @CommandHandler
    fun setzeZeichen(command: SetzeZeichen)
    {
        if (besetzteFelder.contains(command.spielzug.feld))
        {
            throw FeldBelegt(id, command.spielzug.spieler)
        }

        if (letzterSpieler == command.spielzug.spieler)
        {
            throw SpielerNichtAndDerReiheGewesen(id, command.spielzug.spieler)
        }

        AggregateLifecycle.apply(
                SpielzugWurdeAkzeptiert(id, command.spielzug.spieler, command.spielzug.feld))

        if (wirdSpielerGewinnen(Spielzug(command.spielzug.spieler, command.spielzug.feld)))
        {
            AggregateLifecycle.apply(SpielGewonnen(id, command.spielzug.spieler))
        }
    }

    private fun wirdSpielerGewinnen(spielzug: Spielzug): Boolean
    {
        val gewinn = listOf(Feld('A', 1), Feld('B', 2), Feld('C', 3))

        val gewinn2 = listOf(Feld('B', 1), Feld('B', 2), Feld('B', 3))

        val gewinne = listOf(gewinn, gewinn2)

        val felderDesSpieler = (spielverlauf + spielzug)
                .filter { it.spieler == spielzug.spieler }
                .map { it.feld }

        return gewinne.map { felderDesSpieler.containsAll(it) }
                .contains(true)
    }

    @EventSourcingHandler
    fun falls(event: SpielBegonnen)
    {
        id = event.id
    }

    @EventSourcingHandler
    fun falls(event: SpielzugWurdeAkzeptiert)
    {
        spielverlauf += Spielzug(event.spieler, event.feld)
    }
}