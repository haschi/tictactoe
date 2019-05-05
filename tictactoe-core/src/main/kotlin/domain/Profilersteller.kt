package com.github.haschi.tictactoe.domain

import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class Profilersteller {
    @QueryHandler
    fun beantworte(frage: WelcheEigenschaftenBesitztAnwender): Anwendereigenschaften {
        return Anwendereigenschaften("Matthias")
    }
}