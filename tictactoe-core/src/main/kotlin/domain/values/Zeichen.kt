package com.github.haschi.tictactoe.domain.values

data class Zeichen(val wert: Char) {
    init {
        if (!listOf('X', 'O', ' ').contains(wert)) {
            throw UnzulässigesZeichen(wert)
        }
    }

    companion object {
        val Keins: Zeichen get() = Zeichen(' ')

        val X = Zeichen('X')
        val O = Zeichen('O')
    }
}