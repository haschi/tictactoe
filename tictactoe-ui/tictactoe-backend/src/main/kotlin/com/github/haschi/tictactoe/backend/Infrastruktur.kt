package com.github.haschi.tictactoe.backend

import com.github.haschi.tictactoe.application.TicTacToeGateway
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandTargetResolver
import org.axonframework.commandhandling.MetaDataCommandTargetResolver
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class Infrastruktur {
    @Bean
    fun gatewayErzeugen(commandBus: CommandBus): TicTacToeGateway {
        val factory = CommandGatewayFactory(commandBus)

        return factory.createGateway(TicTacToeGateway::class.java)
    }

    @Bean
    fun eventStore(): EventStorageEngine {
        return InMemoryEventStorageEngine()
    }

    @Bean
    fun metaDataCommandTargetResolver(): CommandTargetResolver {
        return MetaDataCommandTargetResolver("id")
    }
}