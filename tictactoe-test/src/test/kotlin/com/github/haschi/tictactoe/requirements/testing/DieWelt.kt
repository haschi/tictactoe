package com.github.haschi.tictactoe.requirements.testing

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DieWelt(val commandGateway: CommandGateway)
{
    fun reset()
    {
        spielername = ""
        spielId = Aggregatkennung.nil
        future = CompletableFuture.supplyAsync { Aggregatkennung.neu() }
        events = listOf()
    }

    var events = listOf<Any>()

    var spielername: String = ""
    var spielId: Aggregatkennung = Aggregatkennung.nil
    var future: CompletableFuture<Any> = CompletableFuture.supplyAsync { Aggregatkennung.neu() }

    fun send(command: Any)
    {
        future = future.thenCombine(
                commandGateway.send<Any>(command)){
            _, second -> second
        }
    }

    @EventHandler
    fun falls(event: Any)
    {
        events += listOf(event)
    }
}