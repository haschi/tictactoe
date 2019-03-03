package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.hateoas.EntityLinks
import org.springframework.http.HttpHeaders
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass

fun CompletableFuture<Aggregatkennung>.locationHeader(
    links: EntityLinks,
    entityClass: KClass<*>
): CompletableFuture<HttpHeaders> {
    return this.thenApply { id ->
        val headers = HttpHeaders()
        headers.location = links.linkForSingleResource(entityClass.java, id).toUri()
        headers
    }
}