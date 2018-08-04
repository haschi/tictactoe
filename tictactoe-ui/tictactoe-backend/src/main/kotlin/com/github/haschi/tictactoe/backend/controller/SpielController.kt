package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielfeld
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder


@RestController
// @RequestMapping("/api/spiel")
class SpielController(private val tictactoe: TicTacToeGateway, private val queryGateway: QueryGateway) {

    @RequestMapping(path = ["/api/spiel"], method = [RequestMethod.POST])
    fun post(@RequestBody spiel: SpielResource, builder: UriComponentsBuilder): ResponseEntity<*> {
        val id = tictactoe.send(BeginneSpiel(spiel.id), spiel.id.toString()).get()
        val location = builder.path("/api/spiel/{id}").buildAndExpand(id)

        return ResponseEntity.created(location.toUri()).build<Any>()
    }

    @RequestMapping(path = ["/api/spiel/{id}"], method = [RequestMethod.GET])
    fun `get`(@PathVariable("id") id: Aggregatkennung): Spielfeld {
        val spielfeldFuture = queryGateway.query(SpielfeldQuery(id), Spielfeld::class.java)
        return spielfeldFuture.get()
    }

    @RequestMapping(path = ["/api/spiel/{id}"], method = [RequestMethod.PUT])
    fun put(@PathVariable("id") id: Aggregatkennung, @RequestBody spielzug: SpielzugResource) {
        tictactoe.send(
            SetzeZeichen(id, Spielzug(spielzug.spieler, spielzug.feld)),
            id.toString()
        )
            .get()
    }
}