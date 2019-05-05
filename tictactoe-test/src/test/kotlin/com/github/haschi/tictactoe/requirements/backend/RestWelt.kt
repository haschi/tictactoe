package com.github.haschi.tictactoe.requirements.backend

import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.core.testing.Person
import com.github.haschi.tictactoe.requirements.shared.testing.IZustand
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import java.net.URI

@Component
class RestWelt : IZustand {
    override var spieler: Map<Char, Spieler> = emptyMap()
    override var anwender: Map<String, Person> = emptyMap()
    var spiel = URI("#")
    lateinit var spec: ClientResponse
}