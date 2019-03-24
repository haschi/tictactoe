package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

interface Identitätsgenerator {
    fun herstellen(): Aggregatkennung
}