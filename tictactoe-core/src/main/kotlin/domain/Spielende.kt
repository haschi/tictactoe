package com.github.haschi.tictactoe.domain

import application.AnwenderGateway
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import domain.commands.KehreVomSpielZurück
import mu.KLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Service

@Service
class Spielende(val anwender: AnwenderGateway) {

    @EventHandler
    fun falls(event: SpielGewonnen) {
        try {
            anwender.send(KehreVomSpielZurück(event.spieler.anwenderId)).get()
        } catch (exception: Exception) {
            logger.info(exception) { "Kehre vom Spiel zurück: nicht erfolgreich" }
        }

    }

    companion object : KLogging()
}