package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import domain.WelcheAnwenderverzeichnisseGibtEs
import domain.values.AnwenderverzeichnisÜbersicht
import mu.KLogging
import org.axonframework.queryhandling.QueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@RestController
@RequestMapping("/api/anwenderverzeichnisse")
class AnwenderverzeichnisCollectionController(
    @Autowired private val anwenderverzeichnis: AnwenderverzeichnisGateway,
    @Autowired private val queryGateway: QueryGateway,
    @Autowired private val identitätsgenerator: Identitätsgenerator
) {
    @RequestMapping(method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getJson(): Mono<AnwenderverzeichnisCollection> {
        return queryGateway.query(WelcheAnwenderverzeichnisseGibtEs, AnwenderverzeichnisÜbersicht::class.java)
            .toMono()
            .map { übersicht ->
                AnwenderverzeichnisCollection(übersicht.verzeichnisse.map { verzeichnis ->
                    AnwenderverzeichnisResource(
                        verzeichnis
                    )
                })
            }
    }

    @RequestMapping(method = [RequestMethod.GET], produces = [APPLICATION_STREAM_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getStreamJson(): Flux<AnwenderverzeichnisCollection> {
        val subscription = queryGateway.subscriptionQuery(
            WelcheAnwenderverzeichnisseGibtEs,
            AnwenderverzeichnisÜbersicht::class.java,
            AnwenderverzeichnisÜbersicht::class.java
        )
        return subscription
            .initialResult().concatWith(subscription.updates())
            .onErrorResume { error ->
                logger.info(error) { "Subscription Error" }
                Mono.just(AnwenderverzeichnisÜbersicht())
            }
            .onBackpressureDrop { element -> println("Backpressure Drop: $element") }
            .map {
                AnwenderverzeichnisCollection(
                    it.verzeichnisse.map { verzeichnis: Aggregatkennung -> AnwenderverzeichnisResource(verzeichnis) })
            }
            .limitRate(1)
            .doFinally { subscription.close() }
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun post(): ResponseEntity<Mono<Void>> {
        val id = identitätsgenerator.herstellen()

        val result = anwenderverzeichnis.send(LegeAnwenderverzeichnisAn(id))
            .toMono().then()

        return ResponseEntity
            .created(UriTemplate("/api/anwenderverzeichnisse/{id}").expand(id))
            .body(result)
    }

    companion object : KLogging()
}

