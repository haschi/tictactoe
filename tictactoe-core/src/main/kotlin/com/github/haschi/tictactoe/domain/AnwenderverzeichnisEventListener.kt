package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.values.Anwenderübersicht
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Service

@Service
class AnwenderverzeichnisEventListener {
    @QueryHandler
    fun falls(query: `WelcheAnwenderSindBekannt`): Anwenderübersicht {
        return Anwenderübersicht.Leer
    }
}