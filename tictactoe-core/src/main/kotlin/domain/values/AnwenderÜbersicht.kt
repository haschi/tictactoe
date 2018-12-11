package com.github.haschi.tictactoe.domain.values

data class AnwenderÜbersicht(val anwender: Map<String, Aggregatkennung>) {
    companion object {
        val Leer = AnwenderÜbersicht(emptyMap())
    }
}