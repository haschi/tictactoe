package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.application.WarteraumGateway
import com.github.haschi.tictactoe.domain.events.AnwenderverzeichnisAngelegt
import com.github.haschi.tictactoe.domain.events.SpielBegonnen
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
        zustand = CompletableFuture.supplyAsync {
            Zustand.Empty
        }

    }

    var zustand: CompletableFuture<Zustand> = CompletableFuture.supplyAsync {
        Zustand.Empty
    }

    data class Zustand(
        val anwenderverzeichnisId: Aggregatkennung,
        val ich: Person,
        val spielId: Aggregatkennung,
        val warteraumId: Aggregatkennung,
        val anwender: Map<String, Person>,
        val ereignisse: List<Any>
    ) {
        companion object {
            val Empty = Zustand(
                Aggregatkennung.NIL,
                Person("", Aggregatkennung.NIL),
                Aggregatkennung.NIL,
                Aggregatkennung.NIL,
                emptyMap(),
                emptyList()
            )
        }
    }

    data class Ergebnis(
        val zustand: Zustand,
        val fehler: Throwable?
    )

    var stepping = false

    // Führt einen Schritt asynchron aus. Der Schritt transformiert dabei den
    // Zustand.
    inline final fun compose(crossinline block: DieWelt.Zustand.() -> CompletableFuture<Zustand>) {
        if (stepping == true) {
            throw UngültigeSchrittausführung()
        }

        stepping = true
        zustand = zustand.thenCompose { block(it) }
        while (pending.count() > 0) {
            val b = pending.first()
            zustand = b(zustand)
            pending -= b
        }
        stepping = false
    }

    class UngültigeSchrittausführung : RuntimeException()

    var pending: List<(CompletableFuture<Zustand>) -> CompletableFuture<Zustand>> = emptyList()

    inline final fun apply(crossinline block: (Zustand) -> Zustand) {
        pending += { zustand: CompletableFuture<Zustand> -> zustand.thenApply { block(it) } }
    }

    inline final fun join(crossinline block: DieWelt.Ergebnis.() -> Unit) {
        block(
            try {
                Ergebnis(zustand.get(), null)

            } catch (t: Throwable) {
                Ergebnis(Zustand.Empty, t)
            }
        )
    }

    final fun versuche(block: (Zustand) -> Unit) {
        var versuch = 0
        while (versuch < 10) {
            try {
                block(zustand.get(100, TimeUnit.MILLISECONDS))
                versuch = 10
            } catch (t: Throwable) {
                val wartezeit = (10 - versuch) * 100L
                Thread.sleep(wartezeit)
                versuch += 1
                println("Versuch $versuch")
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
        apply {
            it.copy(ereignisse = it.ereignisse + event)
        }
    }

    @EventHandler
    fun falls(event: SpielBegonnen) {
        apply {
            it.copy(ereignisse = it.ereignisse + event, spielId = event.id)
        }
    }

    @EventHandler
    fun falls(event: AnwenderverzeichnisAngelegt) {
        apply {
            it.copy(warteraumId = event.warteraumId, ereignisse = it.ereignisse + event)
        }
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

    val tatsachen: Fakten get() = Fakten(zustand.thenApply { it.ereignisse })


    companion object : KLogging()
}

