package com.github.haschi.tictactoe.requirements.core.testing

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.SpielerGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service


@Service
class Infrastructure(@Autowired private val mapper: ObjectMapper) {
    @Bean
    fun ticTacToeGateway(commandBus: CommandBus): TicTacToeGateway {
        val factory = CommandGatewayFactory(commandBus)

        return factory.createGateway(TicTacToeGateway::class.java)
    }

    @Bean
    fun anwenderverzeichnisGateway(commandBus: CommandBus): AnwenderverzeichnisGateway {
        val factory = CommandGatewayFactory(commandBus)
        return factory.createGateway(AnwenderverzeichnisGateway::class.java)
    }

    @Bean
    fun spielerGateway(commandBus: CommandBus): SpielerGateway {
        val factory = CommandGatewayFactory(commandBus)
        return factory.createGateway(SpielerGateway::class.java)
    }

    @Bean()
    @Qualifier("eventSerializer")
    fun serializer(): Serializer {
        return JacksonSerializer(mapper)
    }

    @Qualifier("messageSerializer")
    @Bean()
    fun messageSerializer(): Serializer {
        return JacksonSerializer(mapper)
    }
}