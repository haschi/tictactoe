package com.github.haschi.tictactoe.domain.values

import java.net.URI
import java.util.*


data class Aggregatkennung(val id: String) {

    constructor() : this(UUID.randomUUID())
    //
    constructor(id: URI) : this(id.toString())

    //
    constructor(uuid: UUID) : this(uuid.toString())

    override fun toString(): String {
        return id
    }

    companion object {
        val NIL: Aggregatkennung = Aggregatkennung("")
    }
}
