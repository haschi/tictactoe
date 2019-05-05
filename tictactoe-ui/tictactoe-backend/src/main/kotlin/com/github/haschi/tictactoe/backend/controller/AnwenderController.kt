package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.Anwendereigenschaften
import com.github.haschi.tictactoe.domain.WelcheEigenschaftenBesitztAnwender
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@RestController
@RequestMapping("api/anwender/{id}")
class AnwenderController(private val queryGateway: QueryGateway) {

    @RequestMapping(method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun `get`(@PathVariable("id") id: Aggregatkennung): Mono<AnwenderResource> {
        return queryGateway.query(
            WelcheEigenschaftenBesitztAnwender(id),
            Anwendereigenschaften::class.java
        )
            .toMono()
            .map { AnwenderResource(id, it) }
    }

    @RequestMapping(method = [RequestMethod.PATCH])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun patch(@PathVariable("id") id: Aggregatkennung): Mono<Void> {
        return Mono.empty<Void>()
    }
}