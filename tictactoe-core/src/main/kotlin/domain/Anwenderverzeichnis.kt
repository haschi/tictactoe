package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.RichteWarteraumEin
import com.github.haschi.tictactoe.domain.events.AnwenderGefunden
import com.github.haschi.tictactoe.domain.events.AnwenderNichtGefunden
import com.github.haschi.tictactoe.domain.events.AnwenderverzeichnisAngelegt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.net.URI
import java.util.*

@Aggregate
class Anwenderverzeichnis() {

    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    private lateinit var warteraumId: Aggregatkennung
    private var anwender = emptyMap<String, Aggregatkennung>()

    @CommandHandler
    constructor(command: LegeAnwenderverzeichnisAn) : this() {
        val warteraumId = Aggregatkennung()
        AggregateLifecycle.apply(AnwenderverzeichnisAngelegt(command.id, warteraumId))
        AggregateLifecycle.createNew(Warteraum::class.java) { Warteraum(RichteWarteraumEin(warteraumId)) }
    }

    @CommandHandler
    fun bearbeite(command: RegistriereAnwender) {
        if (!anwender.containsKey(command.name)) {
            AggregateLifecycle.apply(AnwenderNichtGefunden(command.name))
            AggregateLifecycle.createNew(Anwender::class.java) { Anwender(command.name, warteraumId) }
        } else {
            AggregateLifecycle.apply(AnwenderGefunden(command.name))
        }
    }

    @EventSourcingHandler
    fun falls(event: AnwenderverzeichnisAngelegt) {
        id = event.id
        warteraumId = event.warteraumId
    }

    @EventSourcingHandler
    fun falls(event: AnwenderNichtGefunden) {
        anwender += event.name to Aggregatkennung(UUID.randomUUID())
    }

    companion object {
        val ID = Aggregatkennung(URI("singleton", "Anwenderverzeichnis", ""))
    }
}