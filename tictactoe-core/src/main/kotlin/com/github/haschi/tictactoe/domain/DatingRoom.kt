package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BetreteDatingRoom
import com.github.haschi.tictactoe.domain.commands.RichteDatingRoomEin
import com.github.haschi.tictactoe.domain.events.SpielerHatDatingRoomBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.DatingRoomEingerichtet
import com.github.haschi.tictactoe.domain.values.Spieler
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import java.net.URI

@Aggregate
class DatingRoom() {

    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    @CommandHandler
    constructor(command: RichteDatingRoomEin) : this() {
        AggregateLifecycle.apply(DatingRoomEingerichtet(command.id))
    }

    @CommandHandler
    fun verarbeite(command: BetreteDatingRoom) {
        partnerLoseSpieler
            .asSequence()
            .firstOrNull { it.value.zeichen != command.spieler.zeichen }
            ?.let { AggregateLifecycle.apply(SpielpartnerGefunden(it.value, command.spieler)) }
            ?: AggregateLifecycle.apply(SpielerHatDatingRoomBetreten(command.spielerId, command.spieler))
    }

    @EventSourcingHandler
    fun falls(event: DatingRoomEingerichtet) {
        id = event.id
    }

    private var partnerLoseSpieler: Map<String, Spieler> = emptyMap()

    @EventSourcingHandler
    fun falls(event: SpielerHatDatingRoomBetreten) {
        partnerLoseSpieler += event.id to event.spieler
    }

    @EventSourcingHandler
    fun falls(event: SpielpartnerGefunden) {
        partnerLoseSpieler -= event.x.anwender
        partnerLoseSpieler -= event.y.anwender
    }

    companion object {
        val ID = Aggregatkennung(
            URI("singleton", "DatingRoom", "")
        )
    }
}
