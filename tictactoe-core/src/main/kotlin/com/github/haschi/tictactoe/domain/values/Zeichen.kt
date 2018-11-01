package com.github.haschi.tictactoe.domain.values

data class Zeichen(val wert: Char) {
    init {
        if (!listOf('X', 'O', ' ').contains(wert)) {
            throw UnzulässigesZeichen(wert)
        }
    }
}