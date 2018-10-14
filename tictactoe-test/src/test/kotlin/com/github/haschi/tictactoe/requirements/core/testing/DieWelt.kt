package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DieWelt(
    val tictactoe: TicTacToeGateway,
    val anwenderverzeichnis: AnwenderverzeichnisGateway,
    val queryGateway: QueryGateway
) {
    fun reset() {
        ich = ""
        spielId = Aggregatkennung.NIL
        future = CompletableFuture.supplyAsync { Aggregatkennung() }
        events = listOf()
    }

    var events = listOf<Any>()

    var ich: String = ""
    var spielId: Aggregatkennung = Aggregatkennung.NIL
    var future: CompletableFuture<Any> = CompletableFuture.supplyAsync { Aggregatkennung() }

    fun <T> next(send: DieWelt.() -> CompletableFuture<T>): DieWelt {
        future = future.thenCombine(send()) { _, second -> second }
        return this
    }

    fun <R, Q> ask(question: Q, responseType: Class<R>): CompletableFuture<R> {
        return queryGateway.query(question, responseType)
    }

    @EventHandler
    fun falls(event: Any) {
        events += listOf(event)
    }
}