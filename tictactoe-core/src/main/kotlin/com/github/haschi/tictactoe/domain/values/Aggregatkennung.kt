package com.github.haschi.tictactoe.domain.values

import java.util.UUID

data class Aggregatkennung(val id: UUID)
{
    companion object
    {
        fun neu(): Aggregatkennung
        {
            return Aggregatkennung(UUID.randomUUID())
        }

        fun aus(id: String): Aggregatkennung
        {
            return Aggregatkennung(UUID.fromString(id))
        }

        val nil: Aggregatkennung = Aggregatkennung(UUID(0, 0))
    }
}