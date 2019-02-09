package com.github.haschi.tictactoe.domain.values

data class Spieler(val zeichen: Char, val anwender: String, val anwenderId: Aggregatkennung) {

    init {
        if (!listOf('X', 'O', ' ').contains(zeichen)) {
            throw UnzulässigesZeichen(zeichen)
        }
    }

    companion object {
        val Keiner = Spieler(' ', "", Aggregatkennung.NIL)
    }
}
