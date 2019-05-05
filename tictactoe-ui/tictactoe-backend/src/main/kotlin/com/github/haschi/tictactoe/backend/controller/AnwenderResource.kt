package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.Anwendereigenschaften
import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class AnwenderResource(val id: Aggregatkennung, val eigenschaften: Anwendereigenschaften)