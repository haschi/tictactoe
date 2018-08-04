package com.github.haschi.tictactoe.domain.values

data class Spielfeld(val felder: List<Char>) {
    fun hinzufügen(spielzug: Spielzug): Spielfeld {

        val index = spielzug.feld.index
        return Spielfeld(
            felder.mapIndexed { i: Int, c: Char ->
                when (i) {
                    index -> spielzug.spieler.zeichen
                    else -> c
                }
            }
        )
    }

    fun inhalt(feld: Feld): Char {
        return felder[feld.index]
    }

    companion object {
        val leer = Spielfeld(listOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '))
    }
}
