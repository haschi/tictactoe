package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.application.WarteraumGateway
import com.github.haschi.tictactoe.domain.commands.BetreteWarteraum
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Service

@Service
class AnwenderEventListener(val gateway: WarteraumGateway) {
    @EventHandler
    fun falls(event: ZeichenAusgewählt) {
        gateway.send(BetreteWarteraum(event.zugewiesenerWarteraum, event.id, event.spieler))
        // TODO: Fehlerbehandlung
    }
}