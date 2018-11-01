package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BetreteDatingRoom
import com.github.haschi.tictactoe.domain.commands.RichteDatingRoomEin
import com.github.haschi.tictactoe.domain.events.SpielerHatDatingRoomBetreten
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.DatingRoomEingerichtet
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
        AggregateLifecycle.apply(SpielerHatDatingRoomBetreten(command.spielerId, command.spieler))
    }

    @EventSourcingHandler
    fun falls(event: DatingRoomEingerichtet) {
        id = event.id
    }

    companion object {
        val ID = Aggregatkennung(

            URI("singleton", "DatingRoom", "")
        )
    }
}
