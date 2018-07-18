package com.github.haschi.tictactoe.requirements.testing

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
open class DieWelt
{
    var events = listOf<Any>()

    var spielername: String = ""
    var spielId: Aggregatkennung = Aggregatkennung.nil

    @EventHandler
    fun falls(event: Any)
    {
        println("Ereignis aufgetreten: $event")
        events += listOf(event);
    }
}