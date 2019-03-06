package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/anwenderverzeichnisse")
@ExposesResourceFor(AnwenderverzeichnisResource::class)
class AnwenderverzeichnisController(
    private val links: EntityLinks,
    private val anwenderverzeichnis: AnwenderverzeichnisGateway
) {

    @RequestMapping(method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody body: LegeAnwenderverzeichnisAn): CompletableFuture<HttpHeaders> {
        return anwenderverzeichnis.send(body)
            .locationHeader(links, AnwenderverzeichnisResource::class)
    }
}

