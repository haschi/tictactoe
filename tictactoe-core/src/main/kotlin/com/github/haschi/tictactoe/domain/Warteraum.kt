package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.commands.BetreteWarteraum
import com.github.haschi.tictactoe.domain.commands.LegeMaximaleWartezeitFest
import com.github.haschi.tictactoe.domain.commands.RichteWarteraumEin
import com.github.haschi.tictactoe.domain.events.MaximaleWartezeitFestgelegt
import com.github.haschi.tictactoe.domain.events.SpielerHatWarteraumBetreten
import com.github.haschi.tictactoe.domain.events.SpielpartnerGefunden
import com.github.haschi.tictactoe.domain.events.WarteraumVerlassen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.WarteraumEingerichtet
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.annotation.DeadlineHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import java.net.URI
import java.time.Duration

@Aggregate
class Warteraum() {

    @AggregateIdentifier
    private lateinit var id: Aggregatkennung

    private var wartezeit = Duration.ofMinutes(5)

    @CommandHandler
    constructor(command: RichteWarteraumEin) : this() {
        AggregateLifecycle.apply(WarteraumEingerichtet(command.id))
    }

    @CommandHandler
    fun verarbeite(command: BetreteWarteraum, deadlineManager: DeadlineManager) {
        val partner = partnerLoseSpieler
            .asSequence()
            .firstOrNull { it.value.zeichen != command.spieler.zeichen }

        if (partner == null) {
            AggregateLifecycle.apply(SpielerHatWarteraumBetreten(command.spielerId, command.spieler))
            deadlineManager.schedule(wartezeit, "wartezeitBeendet", command.spielerId)
        } else {
            AggregateLifecycle.apply(SpielpartnerGefunden(partner.value, command.spieler))
        }
    }

    @CommandHandler
    fun verarbeite(command: LegeMaximaleWartezeitFest) {
        AggregateLifecycle.apply(
            MaximaleWartezeitFestgelegt(command.wartezeit)
        )
    }

    @EventSourcingHandler
    fun falls(event: WarteraumEingerichtet) {
        id = event.id
    }

    private var partnerLoseSpieler: Map<String, Spieler> = emptyMap()

    @EventSourcingHandler
    fun falls(event: SpielerHatWarteraumBetreten) {
        partnerLoseSpieler += event.id to event.spieler
    }

    @EventSourcingHandler
    fun falls(event: SpielpartnerGefunden) {
        partnerLoseSpieler -= event.x.anwender
        partnerLoseSpieler -= event.y.anwender
    }

    @DeadlineHandler(deadlineName = "wartezeitBeendet")
    fun falls(payload: String) {
        this.partnerLoseSpieler -= payload
        AggregateLifecycle.apply(WarteraumVerlassen(payload))
    }

    @EventSourcingHandler
    fun falls(event: MaximaleWartezeitFestgelegt) {
        wartezeit = event.wartezeit
    }

    companion object {
        val ID = Aggregatkennung(
            URI("singleton", "Warteraum", "")
        )
    }
}
