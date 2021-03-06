package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.domain.events.AnwenderverzeichnisAngelegt
import com.github.haschi.tictactoe.domain.values.AnwenderÜbersicht
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Service

@Service
class AnwenderverzeichnisEventListener(val emitter: QueryUpdateEmitter) {

    private var übersicht = AnwenderverzeichnisÜbersicht()

    @QueryHandler(queryName = "com.github.haschi.tictactoe.domain.WelcheAnwenderSindBekannt")
    fun beantworte(frage: WelcheAnwenderSindBekannt): AnwenderÜbersicht {
        return AnwenderÜbersicht.Leer
    }

    @QueryHandler
    fun beantworte(frage: WelcheAnwenderverzeichnisseGibtEs): AnwenderverzeichnisÜbersicht {
        return übersicht
    }

    @EventHandler
    fun falls(ereignis: AnwenderverzeichnisAngelegt) {
        übersicht = AnwenderverzeichnisÜbersicht(übersicht.verzeichnisse + ereignis.id)
        emitter.emit(WelcheAnwenderverzeichnisseGibtEs::class.java, { true }, übersicht)
    }
}