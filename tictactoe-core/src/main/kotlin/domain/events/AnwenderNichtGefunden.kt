package com.github.haschi.tictactoe.domain.events

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class AnwenderNichtGefunden(val name: String, val anwenderId: Aggregatkennung)
