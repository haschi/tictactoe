package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.application.WarteraumGateway
import com.github.haschi.tictactoe.domain.events.AnwenderRegistriert
import com.github.haschi.tictactoe.domain.events.AnwenderverzeichnisAngelegt
import com.github.haschi.tictactoe.domain.events.SpielBegonnen
import com.github.haschi.tictactoe.domain.events.ZeichenAusgewählt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.shared.testing.IZustand
import com.github.haschi.tictactoe.requirements.shared.testing.Resolver
import mu.KLogging
import org.assertj.core.api.Assertions.fail
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture
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
        val ereignisse: List<Any>,
        override val spieler: Map<Char, Spieler>
    ) : IZustand {
        companion object {
            val Empty = Zustand(
                Aggregatkennung.NIL,
                Person("", Aggregatkennung.NIL),
                Aggregatkennung.NIL,
                Aggregatkennung.NIL,
                emptyMap(),
                emptyList(),
                emptyMap()
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
        if (stepping) {
            throw UngültigeSchrittausführung()
        }

        stepping = true
        zustand = zustand.thenCompose {
            block(it).handle { zustand, throwable ->
                zustand ?: throw AusführungGescheitert(it, throwable.cause!!)
            }
        }

        while (pending.count() > 0) {
            val b = pending.first()
            zustand = b(zustand)
            pending = pending - b
        }
        stepping = false
    }

    class UngültigeSchrittausführung : RuntimeException()

    var pending: List<(CompletableFuture<Zustand>) -> CompletableFuture<Zustand>> = emptyList()

    inline final fun apply(crossinline block: (Zustand) -> Zustand) {
        pending = pending + { zustand: CompletableFuture<Zustand> -> zustand.thenApply { block(it) } }
    }

    inline final fun join(crossinline block: DieWelt.Ergebnis.() -> Unit) {
        block(
            try {
                Ergebnis(zustand.get(), null)

            } catch (t: Throwable) {
                try {
                    Ergebnis(zustand.get(), t)
                } catch (t2: Throwable) {
                    Ergebnis(Zustand.Empty, t2)
                }
            }
        )
    }

    final fun exceptionally(block: (AusführungGescheitert) -> Zustand) {
        zustand = zustand.exceptionally {
            val cause = it.cause
            when (cause) {
                is AusführungGescheitert -> block(cause)
                else -> fail("Unerwartete Ausnahme", cause)
            }
        }
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
    fun falls(event: AnwenderverzeichnisAngelegt) {
        apply {
            it.copy(warteraumId = event.warteraumId, ereignisse = it.ereignisse + event)
        }
    }

    @EventHandler
    fun falls(event: ZeichenAusgewählt) {
        apply {
            it.copy(ereignisse = it.ereignisse + event)
        }
    }

    @EventHandler
    fun falls(event: SpielBegonnen) {
        apply {
            it.copy(
                spielId = event.id,
                spieler = mapOf('X' to event.x, 'O' to event.o),
                ereignisse = it.ereignisse + event
            )
        }
    }

    @EventHandler
    fun falls(event: AnwenderRegistriert) {
        apply {
            it.copy(
                anwender = it.anwender + (event.name to Person(event.name, event.id)),
                ereignisse = it.ereignisse + event
            )
        }
    }

    companion object : KLogging()
}

data class AusführungGescheitert(val zustand: DieWelt.Zustand, val throwable: Throwable) : Exception() {
    fun <T> resolve(resolver: Resolver<T>): T {
        return resolver.resolve(zustand)
    }
}

