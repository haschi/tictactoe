package com.github.haschi.tictactoe.domain.values

data class Anwenderübersicht(val anwender: Map<String, Aggregatkennung>) {
    companion object {
        val Leer = Anwenderübersicht(emptyMap())
    }
}