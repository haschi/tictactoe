package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielfeld
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.MediaTypes.HAL_JSON_UTF8_VALUE
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import java.util.concurrent.CompletableFuture


@RestController
@ExposesResourceFor(Spielfeld::class)
@RequestMapping("/api/spiel")
class SpielController(
    private val tictactoe: TicTacToeGateway,
    private val queryGateway: QueryGateway,
    private val links: EntityLinks
) {

    @RequestMapping(path = ["{id}"], method = [POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@PathVariable("id") id: Aggregatkennung): CompletableFuture<HttpHeaders> {
        return tictactoe.send(BeginneSpiel(id), id.toString())
            .thenApply {
                val headers = HttpHeaders()
                headers.location = links.linkForSingleResource(Spielfeld::class.java, id).toUri()

                headers
            }
    }

    @RequestMapping(
        path = ["{id}"],
        method = [GET],
        produces = [HAL_JSON_UTF8_VALUE]
    )
    @ResponseStatus(HttpStatus.OK)
    fun `get`(@PathVariable("id") id: Aggregatkennung): CompletableFuture<SpielfeldResource> {
        val l = links.linkForSingleResource(Spielfeld::class.java, id.toString())
        println(l)
        return queryGateway.query(SpielfeldQuery(id), Spielfeld::class.java)
            .thenApply {
                val ls = SpielfeldResource(it)
                ls.add(links.linkForSingleResource(Spielfeld::class.java, id.toString()).withSelfRel())
                ls
            }
    }

    @RequestMapping(path = ["{id}"], method = [PUT])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: Aggregatkennung, @RequestBody spielzug: SpielzugResource): CompletableFuture<Void> {
        return tictactoe.send(
            SetzeZeichen(id, Spielzug(spielzug.spieler, spielzug.feld)),
            id.toString()
        )
    }
}