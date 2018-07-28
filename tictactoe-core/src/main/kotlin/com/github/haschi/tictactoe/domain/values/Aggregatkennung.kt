package com.github.haschi.tictactoe.domain.values

import org.axonframework.common.IdentifierFactory
import java.net.URI
import java.util.*

data class Aggregatkennung(val id: String = IdentifierFactory.getInstance().generateIdentifier()) {
    constructor(id: URI) : this(id.toString())

    constructor(randomUUID: UUID) : this(randomUUID.toString())

    companion object {
        @JvmField
        val NIL: Aggregatkennung = Aggregatkennung(UUID(0, 0))
    }

    override fun toString(): String {
        return id
    }
}
