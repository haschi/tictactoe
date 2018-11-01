package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Spieler

data class SpielerHatDatingRoomBetreten(val id: String, val spieler: Spieler)
