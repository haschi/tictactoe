package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.values.AnwenderÜbersicht
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class AnwenderverzeichnisEventListener {
    @QueryHandler(queryName = "com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt")
    fun beantworte(frage: WelcheAnwenderSindBekannt): AnwenderÜbersicht {
        return AnwenderÜbersicht.Leer
    }
}