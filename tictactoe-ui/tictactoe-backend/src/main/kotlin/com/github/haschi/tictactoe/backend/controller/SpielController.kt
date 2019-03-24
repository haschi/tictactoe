package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.SpielfeldQuery
import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spielfeld
import com.github.haschi.tictactoe.domain.values.Spielzug
import mu.KLogging
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.util.UriTemplate
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/spiel")
class SpielController(
    private val tictactoe: TicTacToeGateway,
    private val queryGateway: QueryGateway
) {

    @RequestMapping(path = ["/{id}"], method = [POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@PathVariable("id") id: UUID, @RequestBody s: Mono<SpielerParameter>): ResponseEntity<Mono<Void>> {
        return ResponseEntity.created(UriTemplate("/api/spiel/{id}").expand(id))
            .body(s.flatMap {
                tictactoe.send(BeginneSpiel(Aggregatkennung(id), it.x, it.o))
                    .toMono().then()
            })
    }


    @RequestMapping(path = ["{id}"], method = [GET])
    @ResponseStatus(HttpStatus.OK)
    fun `get`(@PathVariable("id") id: UUID): CompletableFuture<SpielfeldResource> {
        return queryGateway.query(SpielfeldQuery(Aggregatkennung(id)), Spielfeld::class.java)
            .thenApply {
                val ls = SpielfeldResource(it)
//                ls.add(links.linkForSingleResource(Spielfeld::class.java, id.toString()).withSelfRel())
                ls
            }
    }

    @RequestMapping(
        path = ["{id}"],
        method = [PUT],
        produces = [MediaType.APPLICATION_JSON_VALUE, VndError.ERROR_JSON_UTF8_VALUE]
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: UUID, @RequestBody spielzug: Mono<SpielzugResource>): Mono<Void> {

        return spielzug.flatMap {
            tictactoe.send(
                SetzeZeichen(Aggregatkennung(id), Spielzug(it.spieler, it.feld))
            ).toMono()
                .then()
        }
    }

    companion object : KLogging()
}