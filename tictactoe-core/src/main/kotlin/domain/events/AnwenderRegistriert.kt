package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class AnwenderRegistriert(
    val name: String,
    val zugewiesenerWarteraum: Aggregatkennung
)
