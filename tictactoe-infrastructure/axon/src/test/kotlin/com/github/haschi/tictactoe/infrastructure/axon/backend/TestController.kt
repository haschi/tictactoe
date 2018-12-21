package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/test")
class TestController(private val gateway: CommandGateway) {

    @RequestMapping(method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.CREATED)
    fun post(): CompletableFuture<HttpHeaders> {
        return gateway.send<String>("Hallo")
            .thenApply { HttpHeaders() }
    }

}
