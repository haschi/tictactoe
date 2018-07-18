

package com.github.haschi.tictactoe.requirements

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.SpielzugWurdeAkzeptiert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.testing.DieWelt
import com.github.haschi.tictactoe.requirements.testing.FeldConverter
import com.github.haschi.tictactoe.requirements.testing.SpielerConverter
import cucumber.api.PendingException
import cucumber.api.Transform
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import cucumber.api.java.de.Wenn
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventsourcing.eventstore.EventStore

class SpielsteinSetzen(val welt: DieWelt, val commandGateway: CommandGateway, val eventStore: EventStore)
{
    @Angenommen("^ich habe das Spiel begonnen$")
    fun ich_habe_das_Spiel_begonnen()
    {
        welt.spielId = commandGateway.sendAndWait(BeginneSpiel(Aggregatkennung.neu()))
        println(welt.spielId)
        dump()
    }

    @Wenn("^Spieler (X|O) sein Zeichen auf Feld ([ABC][123]) setzt$")
    fun spieler_X_sein_Zeichen_auf_Feld_B_setzt(
            @Transform(SpielerConverter::class) spieler: Spieler,
            @Transform(FeldConverter::class) feld: Feld)
    {
        dump()
        println(welt.spielId)
        commandGateway.sendAndWait<Any>(SetzeZeichen(welt.spielId, spieler, feld))
    }

    @Dann("^werde ich den Spielzug ([ABC][123]) von Spieler (X|O) akzeptiert haben$")
    fun werde_ich_den_Spielzug_B_von_Spieler_X_akzeptiert_haben(
            @Transform(FeldConverter::class) feld: Feld,
            @Transform(SpielerConverter::class) spieler: Spieler)
    {
        assertThat(welt.events).contains(
                SpielzugWurdeAkzeptiert(welt.spielId, spieler, feld))
    }

    fun dump()
    {
        println("Event Store:")

        eventStore.readEvents(welt.spielId.toString()).asStream()
                .forEach { println(it) }
    }
}
