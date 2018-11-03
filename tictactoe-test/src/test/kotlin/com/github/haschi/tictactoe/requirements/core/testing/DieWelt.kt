package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.SpielerGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Component
class DieWelt(
    val tictactoe: TicTacToeGateway,
    val anwenderverzeichnis: AnwenderverzeichnisGateway,
    val spieler: SpielerGateway,
    val queryGateway: QueryGateway
) {
    fun reset() {
        ich = Person("", Aggregatkennung.NIL)
        spielId = Aggregatkennung.NIL
        future = CompletableFuture.supplyAsync { Aggregatkennung() }
        fakten = listOf()
    }

    var fakten = listOf<Any>()

    var ich = Person("", Aggregatkennung.NIL)
    var spielId: Aggregatkennung = Aggregatkennung.NIL
    var future: CompletableFuture<Any> = CompletableFuture.supplyAsync { Aggregatkennung() }

    fun <T> next(send: DieWelt.() -> CompletableFuture<T>): DieWelt {
        future = future.thenCombine(send()) { _, second -> second }
        return this
    }

    fun <T> schritt(vararg send: DieWelt.() -> CompletableFuture<T>): DieWelt {
        send.forEach {
            future = future.thenCombine(it()) { _, second -> second }
        }

        return this
    }

    fun <R, Q> ask(question: Q, responseType: Class<R>): CompletableFuture<R> {
        return queryGateway.query(question, responseType)
    }

    @EventHandler
    fun falls(event: Any) {
        fakten += listOf(event)
    }

    class Fakten(private val fakten: CompletableFuture<List<Any>>) : Future<List<Any>> by fakten {
        infix fun bestätigen(event: Any) {
            assertThat(fakten)
                .isCompletedWithValueMatching({ it.contains(event) }, "Enthält Ereignis $event")
        }
    }

    val tatsachen: Fakten get() = Fakten(future.thenApply { fakten })

//    private fun assert(event: Any) {
//        tatsachen bestätigen  event
//    }

    operator fun invoke(body: DieWelt.() -> Unit) {
        body()
    }

//    infix fun bestäge(block: DieWelt.() -> Any) {
//        assert(block())
//    }

//    infix fun bestätige(fakt: Any) {
//        assert(fakt)
//    }
}

