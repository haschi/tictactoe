package com.github.haschi.tictactoe.domain.values

data class Feld(val spalte: Char, val zeile: Int) {
    init {
        val erlaubteSpalten = 'A'..'C'

        if (!erlaubteSpalten.contains(spalte)) {
            throw UngültigesFeld(spalte, zeile)
        }

        val erlaubteZeilen = 1..3

        if (!erlaubteZeilen.contains(zeile)) {
            throw UngültigesFeld(spalte, zeile)
        }
    }

    val index get() = zeile * (spalte - 'B')
}
