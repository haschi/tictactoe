package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class Profilersteller {

    private var alle: Map<Aggregatkennung, Anwendereigenschaften> = emptyMap()

    @QueryHandler
    fun beantworte(frage: WelcheEigenschaftenBesitztAnwender): Anwendereigenschaften {
        return alle.getValue(frage.s)
    }

    @EventHandler
    fun falls(ereignis: AnwenderRegistriert) {
        val eigenschaften = Anwendereigenschaften(ereignis.name)
        alle = alle + (ereignis.id to eigenschaften)
    }
}