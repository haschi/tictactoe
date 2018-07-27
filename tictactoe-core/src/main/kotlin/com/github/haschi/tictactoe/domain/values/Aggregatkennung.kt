package com.github.haschi.tictactoe.domain.values

import org.axonframework.common.IdentifierFactory
import java.net.URI
import java.util.*

data class Aggregatkennung(private val id: String = IdentifierFactory.getInstance().generateIdentifier()) {
    constructor(id: URI) : this(id.toString())

    companion object {
        val Nil: Aggregatkennung = Aggregatkennung(UUID(0, 0))
    }

    constructor(randomUUID: UUID) : this(randomUUID.toString())

    override fun toString(): String {
        return id
    }
}
