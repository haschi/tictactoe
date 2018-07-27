package com.github.haschi.tictactoe.requirements.testing

import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DieWelt(val tictactoe: TicTacToeGateway) {
    fun reset() {
        spielername = ""
        spielId = Aggregatkennung.NIL
        future = CompletableFuture.supplyAsync { Aggregatkennung() }
        events = listOf()
    }

    var events = listOf<Any>()

    var spielername: String = ""
    var spielId: Aggregatkennung = Aggregatkennung.NIL
    var future: CompletableFuture<Any> = CompletableFuture.supplyAsync { Aggregatkennung() }

    fun <T> next(send: DieWelt.() -> CompletableFuture<T>) {
        future = future.thenCombine(send()) { _, second -> second }
    }

    @EventHandler
    fun falls(event: Any) {
        events += listOf(event)
    }
}