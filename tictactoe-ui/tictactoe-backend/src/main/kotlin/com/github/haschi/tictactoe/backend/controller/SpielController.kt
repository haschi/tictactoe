package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielfeld
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.CompletableFuture


@RestController
// @RequestMapping("/api/spiel")
class SpielController(private val tictactoe: TicTacToeGateway, private val queryGateway: QueryGateway) {

    @RequestMapping(path = ["/api/spiel/{id}"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@PathVariable("id") id: Aggregatkennung, builder: UriComponentsBuilder): CompletableFuture<HttpHeaders> {
        return tictactoe.send(BeginneSpiel(id), id.toString())
            .thenApply {
                val location = builder.path("/api/spiel/{id}").buildAndExpand(it)

                val headers = HttpHeaders()
                headers.location = location.toUri()

                headers
            }
    }

    @RequestMapping(
        path = ["/api/spiel/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_UTF8_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    fun `get`(@PathVariable("id") id: Aggregatkennung): CompletableFuture<Spielfeld> {
        val spielfeldFuture = queryGateway.query(SpielfeldQuery(id), Spielfeld::class.java)
        return spielfeldFuture
    }

    @Async()
    @RequestMapping(path = ["/api/spiel/{id}"], method = [RequestMethod.PUT])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: Aggregatkennung, @RequestBody spielzug: SpielzugResource): CompletableFuture<Void> {
        return tictactoe.send(
            SetzeZeichen(id, Spielzug(spielzug.spieler, spielzug.feld)),
            id.toString()
        )
    }
}