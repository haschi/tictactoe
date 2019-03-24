package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriTemplate
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.concurrent.CompletableFuture

fun CompletableFuture<Aggregatkennung>.locationHeader(
    uri: UriTemplate
): Mono<HttpHeaders> {
    return this.thenApply { id ->

        val headers = HttpHeaders()

        headers.location = uri.expand(id)
        headers
    }.toMono()
}