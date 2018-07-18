package com.github.haschi.tictactoe.domain.values

class UnzulässigesZeichen(zeichen: Char)
    : Exception("Unzulässiges Zeichen '$zeichen' für Spieler")
