package com.github.haschi.tictactoe.domain

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.BetreteWarteraum
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Service

@Service
class AnwenderEventListener(val gateway: AnwenderverzeichnisGateway) {
    @EventHandler
    fun falls(event: ZeichenAusgewählt) {
        gateway.send(BetreteWarteraum(Warteraum.ID, event.id, event.spieler))
    }
}