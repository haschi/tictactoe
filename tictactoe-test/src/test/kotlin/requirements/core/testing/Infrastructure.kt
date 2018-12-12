package com.github.haschi.tictactoe.requirements.core.testing

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.application.WarteraumGateway
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.config.ConfigurationScopeAwareProvider
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.SimpleDeadlineManager
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.spring.config.AxonConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Infrastructure(@Autowired private val mapper: ObjectMapper) {
    @Bean
    fun commandGatewayFactory(commandBus: CommandBus): CommandGatewayFactory {
        return CommandGatewayFactory.builder()
            .commandBus(commandBus)
            .build()
    }

    @Bean
    fun ticTacToeGateway(commandGatewayFactory: CommandGatewayFactory): TicTacToeGateway {
        return commandGatewayFactory.createGateway(TicTacToeGateway::class.java)
    }

    @Bean
    fun anwenderverzeichnisGateway(commandGatewayFactory: CommandGatewayFactory): AnwenderverzeichnisGateway {
        return commandGatewayFactory.createGateway(AnwenderverzeichnisGateway::class.java)
    }

    @Bean
    fun warteraumGateway(commandGatewayFactory: CommandGatewayFactory): WarteraumGateway {
        return commandGatewayFactory.createGateway(WarteraumGateway::class.java)
    }

    @Bean
    @Qualifier("eventSerializer")
    fun serializer(): Serializer {
        return JacksonSerializer.builder()
            .objectMapper(mapper)
            .build()
    }

    @Qualifier("messageSerializer")
    @Bean
    fun messageSerializer(): Serializer {
        return JacksonSerializer.builder()
            .objectMapper(mapper)
            .build()
    }

    @Bean
    fun deadlineManager(configuration: AxonConfiguration, tm: TransactionManager): DeadlineManager {
        return SimpleDeadlineManager.builder()
            .scopeAwareProvider(ConfigurationScopeAwareProvider(configuration))
            .transactionManager(tm)
            .build()
    }


//    val axonServerConfiguration = AxonServerAutoConfiguration
//    @Bean fun eventBus(): EventBus {
//
//        AxonServerEventStore()
//    }
}