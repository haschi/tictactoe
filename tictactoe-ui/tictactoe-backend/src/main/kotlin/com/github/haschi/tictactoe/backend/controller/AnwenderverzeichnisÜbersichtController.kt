package com.github.haschi.tictactoe.backend.controller

import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import org.axonframework.queryhandling.QueryGateway
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/anwenderverzeichnisse")
@ExposesResourceFor(AnwenderverzeichnisÜbersicht::class)
class AnwenderverzeichnisÜbersichtController(
    private val queryGateway: QueryGateway,
    private val links: EntityLinks
) {

    @RequestMapping(method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun `get`(): CompletableFuture<Resources<Resource<AnwenderverzeichnisResource>>> {
        return queryGateway.query(WelcheAnwenderverzeichnisseGibtEs, AnwenderverzeichnisÜbersicht::class.java)
            .thenApply {
                it.map { verzeichnis ->
                    val resource = AnwenderverzeichnisResource(verzeichnis)
                    Resource(resource, links.linkForSingleResource(resource).withSelfRel())
                }
            }
            .thenApply {
                Resources(it, links.linkToCollectionResource(AnwenderverzeichnisÜbersicht::class.java).withSelfRel())
            }
    }
}

