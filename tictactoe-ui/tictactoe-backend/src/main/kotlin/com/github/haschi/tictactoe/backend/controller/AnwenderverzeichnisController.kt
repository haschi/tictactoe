package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.Anwenderverzeichnis
import org.springframework.hateoas.ExposesResourceFor
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/anwenderverzeichnis")
@ExposesResourceFor(Anwenderverzeichnis::class)
class AnwenderverzeichnisController {

    @RequestMapping(method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(): CompletableFuture<HttpHeaders> {
        return CompletableFuture.supplyAsync {
            HttpHeaders()
        }
    }
}
