package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/anwenderverzeichnisse")
@ExposesResourceFor(AnwenderverzeichnisÜbersicht::class)
class AnwenderverzeichnisCollectionController(
    private val anwenderverzeichnis: AnwenderverzeichnisGateway,
    private val queryGateway: QueryGateway,
    private val links: EntityLinks
) {
    @RequestMapping(method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun `get`(): CompletableFuture<Resources<Resource<AnwenderverzeichnisResource>>> {
        return queryGateway.query(WelcheAnwenderverzeichnisseGibtEs, AnwenderverzeichnisÜbersicht::class.java)
            .thenApply {
                it.map { verzeichnis ->
                    Resource(
                        AnwenderverzeichnisResource(verzeichnis),
                        links.linkForSingleResource(
                            AnwenderverzeichnisResource(verzeichnis)
                        ).withSelfRel()
                    )
                }
            }
            .thenApply {
                Resources(it, links.linkToCollectionResource(AnwenderverzeichnisÜbersicht::class.java).withSelfRel())
            }
    }

    @RequestMapping(method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody body: LegeAnwenderverzeichnisAn): CompletableFuture<HttpHeaders> {
        return anwenderverzeichnis.send(body)
            .locationHeader(links, AnwenderverzeichnisResource::class)
    }
}

