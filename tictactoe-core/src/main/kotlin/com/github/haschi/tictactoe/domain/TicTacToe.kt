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
import org.axonframework.commandhandling.model.AggregateLifecycle.apply
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class TicTacToe
{
    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    private var spielverlauf = listOf<Spielzug>()

    constructor()

    @CommandHandler
    constructor(command: BeginneSpiel)
    {
        apply(SpielBegonnen(command.id))
    }

    @CommandHandler
    fun setzeZeichen(command: SetzeZeichen)
    {
        fallsFeldBelegt(command.spielzug)
        {
            throw FeldBelegt(id, it.spieler)
        }

        fallsSpielerNichtAnDerReiheIst(command.spielzug)
        {
            throw SpielerNichtAndDerReiheGewesen(id, it.spieler)
        }

        apply(SpielzugWurdeAkzeptiert(id, command.spielzug))

        fallsSpielerGewinnt(command.spielzug)
        {
            apply(SpielGewonnen(id, it.spieler))
        }
    }

    private fun fallsSpielerGewinnt(spielzug: Spielzug, dann: (Spielzug) -> Unit)
    {
        val gewinn = listOf(Feld('A', 1), Feld('B', 2), Feld('C', 3))
        val gewinn2 = listOf(Feld('B', 1), Feld('B', 2), Feld('B', 3))
        val gewinne = listOf(gewinn, gewinn2)
        val felderDesSpieler = (spielverlauf + spielzug)
                .filter { it.spieler == spielzug.spieler }
                .map { it.feld }
        if (gewinne.map { felderDesSpieler.containsAll(it) }
                .contains(true))
        {
            dann(spielzug)
        }
    }

    private fun fallsSpielerNichtAnDerReiheIst(spielzug: Spielzug, dann: (Spielzug) -> Unit)
    {
        if (spielverlauf.map { it.spieler }
                .lastOrNull() ?: Spieler.keiner == spielzug.spieler)
        {
            dann(spielzug)
        }
    }

    private fun fallsFeldBelegt(spielzug: Spielzug, dann: (Spielzug) -> Unit)
    {
        if (spielverlauf.map { it.feld }.contains(spielzug.feld))
        {
            dann(spielzug)
        }
    }

    @EventSourcingHandler
    fun falls(event: SpielBegonnen)
    {
        id = event.id
    }

    @EventSourcingHandler
    fun falls(event: SpielzugWurdeAkzeptiert)
    {
        spielverlauf += event.spielzug
    }
}