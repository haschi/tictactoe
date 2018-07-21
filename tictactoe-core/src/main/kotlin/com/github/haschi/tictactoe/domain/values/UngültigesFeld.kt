package com.github.haschi.tictactoe.domain.values

class UngültigesFeld(spalte: Char, zeile: Int)
    : Exception("Ungültiges Feld '$spalte$zeile'")