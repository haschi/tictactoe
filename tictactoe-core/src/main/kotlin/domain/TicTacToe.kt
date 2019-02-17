package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.*
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import domain.events.SpielUnentschiedenBeendet
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class TicTacToe() {
    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    private var spielverlauf = listOf<Spielzug>()

    @CommandHandler
    constructor(command: BeginneSpiel) : this() {
        AggregateLifecycle.apply(SpielBegonnen(command.id, command.x, command.o))
    }

    @CommandHandler
    fun bearbeite(command: SetzeZeichen) {
        `ist Feld bereits belegt?`(command.spielzug)
        {
            throw FeldBelegt(it.spieler)
        }

        fallsSpielerNichtAnDerReiheIst(command.spielzug)
        {
            throw SpielerNichtAndDerReiheGewesen(it.spieler)
        }

        AggregateLifecycle.apply(SpielzugWurdeAkzeptiert(id, command.spielzug))

        `hat Spieler gewonnen?`(command.spielzug)
        {
            AggregateLifecycle.apply(SpielGewonnen(id, it.spieler))
        }

        fallsUnentschieden {
            AggregateLifecycle.apply(SpielUnentschiedenBeendet(id))
        }
    }

    private fun fallsUnentschieden(dann: () -> Unit) {
        if (spielverlauf.size == 8) {
            dann()
        }
    }

    private fun `hat Spieler gewonnen?`(spielzug: Spielzug, dann: (Spielzug) -> Unit) {
        val gewinn = listOf(Feld('A', 1), Feld('B', 2), Feld('C', 3))
        val gewinn2 = listOf(Feld('B', 1), Feld('B', 2), Feld('B', 3))
        val gewinne = listOf(gewinn, gewinn2)
        val felderDesSpieler = (spielverlauf + spielzug)
            .filter { it.spieler == spielzug.spieler }
            .map { it.feld }
        if (gewinne.map { felderDesSpieler.containsAll(it) }
                .contains(true)) {
            dann(spielzug)
        }
    }

    private fun fallsSpielerNichtAnDerReiheIst(spielzug: Spielzug, dann: (Spielzug) -> Unit) {
        if (spielverlauf.map { it.spieler }
                .lastOrNull() ?: Spieler.Keiner == spielzug.spieler) {
            dann(spielzug)
        }
    }

    private fun `ist Feld bereits belegt?`(spielzug: Spielzug, dann: (Spielzug) -> Unit) {
        if (spielverlauf.map { it.feld }.contains(spielzug.feld)) {
            dann(spielzug)
        }
    }

    @EventSourcingHandler
    fun falls(event: SpielBegonnen) {
        id = event.id
    }

    @EventSourcingHandler
    fun falls(event: SpielzugWurdeAkzeptiert) {
        spielverlauf += event.spielzug
    }
}