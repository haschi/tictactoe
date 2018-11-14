package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.Anwenderverzeichnis
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import cucumber.api.Transformer
import org.axonframework.eventsourcing.eventstore.EventStore

class AnwenderConverter(private val store: EventStore) : Transformer<Aggregatkennung>() {
    override fun transform(p0: String?): Aggregatkennung {
        store.readEvents(Anwenderverzeichnis.ID.id)
        return Aggregatkennung.NIL
    }
}