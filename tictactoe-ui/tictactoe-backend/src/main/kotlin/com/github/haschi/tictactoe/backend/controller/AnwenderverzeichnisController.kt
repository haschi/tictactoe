package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriTemplate
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/anwenderverzeichnisse/{id}")
class AnwenderverzeichnisController(
    @Autowired private val queryGateway: QueryGateway,
    @Autowired private val anwenderverzeichnis: AnwenderverzeichnisGateway
) {

    @RequestMapping(method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun `get`(@PathVariable("id") id: Aggregatkennung): Mono<AnwenderverzeichnisResource> {
        return Mono.just(AnwenderverzeichnisResource(id))
    }

    @RequestMapping(method = [RequestMethod.POST], consumes = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun `post`(@PathVariable("id") id: Aggregatkennung, @RequestBody anwender: String): Mono<HttpHeaders> {
        return anwenderverzeichnis.send(RegistriereAnwender(id, anwender))
            .locationHeader(UriTemplate("/api/anwenderverzeichnisse/{id}"))
    }
}

