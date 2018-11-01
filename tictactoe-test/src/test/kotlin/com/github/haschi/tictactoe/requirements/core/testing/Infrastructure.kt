package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.SpielerGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class Infrastructure {
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

//    @Bean
//    fun eventStore(): EventStorageEngine {
//        return InMemoryEventStorageEngine()
//    }

    //@Bean
//    fun metaDataCommandTargetResolver(): CommandTargetResolver {
//        return MetaDataCommandTargetResolver("id")
//    }
}