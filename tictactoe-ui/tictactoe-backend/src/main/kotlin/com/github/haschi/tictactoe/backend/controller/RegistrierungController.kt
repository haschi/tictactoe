package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.Anwender
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/registrierung")
@ExposesResourceFor(Anwender::class)
class RegistrierungController(private val tictactoe: TicTacToeGateway, private val links: EntityLinks) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(): CompletableFuture<HttpHeaders> {
        return tictactoe.send(RegistriereAnwender(Aggregatkennung(), "Matthias")).thenApply { anwenderId ->
            val headers = HttpHeaders()
            headers.location = links.linkForSingleResource(Anwender::class.java, anwenderId).toUri()
            headers
        }
    }
}