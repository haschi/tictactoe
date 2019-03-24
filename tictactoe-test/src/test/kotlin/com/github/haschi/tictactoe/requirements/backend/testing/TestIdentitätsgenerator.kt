package com.github.haschi.tictactoe.requirements.backend.testing

import com.github.haschi.tictactoe.backend.controller.Identitätsgenerator
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.stereotype.Service

@Service
class TestIdentitätsgenerator : Identitätsgenerator {
    override fun herstellen() = Aggregatkennung()
}