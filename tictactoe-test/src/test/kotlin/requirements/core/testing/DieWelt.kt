package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.application.WarteraumGateway
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.TimeUnit

@Component
class DieWelt(
    val tictactoe: TicTacToeGateway,
    val anwenderverzeichnis: AnwenderverzeichnisGateway,
    val warteraum: WarteraumGateway,
    val queryGateway: QueryGateway
) {
    fun reset() {
        ereignisse = listOf()
        zustand = CompletableFuture.supplyAsync {
            Zustand.Empty
        }

    }

    var ereignisse = listOf<Any>()

    var zustand: CompletableFuture<Zustand> = CompletableFuture.supplyAsync {
        Zustand.Empty
    }

    data class Zustand(
        val anwenderverzeichnisId: Aggregatkennung,
        val ich: Person,
        val spielId: Aggregatkennung,
        val warteraumId: Aggregatkennung,
        val anwender: Map<String, Person>
    ) {
        companion object {
            val Empty = Zustand(
                Aggregatkennung.NIL,
                Person("", Aggregatkennung.NIL),
                Aggregatkennung.NIL,
                Aggregatkennung.NIL,
                emptyMap()
            )
        }
    }

    data class Ergebnis(
        val zustand: Zustand,
        val ereignisse: List<Any>,
        val fehler: Throwable?
    )

    // Führt einen Schritt asynchron aus. Der Schritt transformiert dabei den
    // Zustand.
    inline final fun step(crossinline block: DieWelt.Zustand.() -> CompletableFuture<Zustand>) {
        zustand = zustand.thenCompose { block(it) }
    }

    inline final fun join(crossinline block: DieWelt.Ergebnis.() -> Unit) {
        block(
            try {
                Ergebnis(zustand.get(), ereignisse, null)

            } catch (t: Throwable) {
                Ergebnis(Zustand.Empty, ereignisse, t)
            }
        )
    }

    final fun versuche(block: (Zustand, List<Any>) -> Unit) {
        var versuch = 0
        while (versuch < 10) {
            try {
                block(zustand.get(100, TimeUnit.MILLISECONDS), ereignisse)
                versuch = 10
            } catch (t: Throwable) {
                val wartezeit = (10 - versuch) * 100L
                Thread.sleep(wartezeit)
                versuch += 1
                if (versuch == 10) {
                    throw t
                }
            }
        }
    }

    fun <R, Q> ask(question: Q, responseType: Class<R>): CompletableFuture<R> {
        return queryGateway.query(question, responseType)
    }

    @EventHandler
    fun falls(event: Any) {
        ereignisse += event
    }

    class Fakten(fakten: CompletionStage<List<Any>>) : CompletionStage<List<Any>> by fakten {
        infix fun bestätigen(event: Any) {
            assertThat(this)
                .isCompletedWithValueMatching(
                    { it.contains(event) },
                    "Enthält Ereignis $event"
                )
        }
    }

    val tatsachen: Fakten get() = Fakten(zustand.thenApply { ereignisse })


    companion object : KLogging()
}

