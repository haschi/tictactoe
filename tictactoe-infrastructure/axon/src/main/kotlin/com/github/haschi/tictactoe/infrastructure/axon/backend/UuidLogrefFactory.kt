package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.springframework.stereotype.Service
import java.util.*

@Service
class UuidLogrefFactory : LogrefFactory {
    override fun nächsteId(): String {
        return UUID.randomUUID().toString()
    }
}
