package com.github.haschi.tictactoe.requirements.core.testing

import application.AnwenderGateway
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.application.TicTacToeGateway
import com.github.haschi.tictactoe.application.WarteraumGateway
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.CommandGatewayFactory
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.config.ConfigurationScopeAwareProvider
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.SimpleDeadlineManager
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.spring.config.AxonConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Infrastructure(@Autowired private val mapper: ObjectMapper) {


    // Für den Test wird stets ein SimpleCommandBus erzeugt. Die Standard-Konfiguration
    // für den Axon Server verwendet AxonServerCommandBus, der im Fehlerfall Domain Exceptions
    // in AxonServerRemoteCommandHandlingException kapselt. Dabei bleibt zwar die Fehlermeldung
    // im Feld exceptionDescriptions bestehen, aber der Typ geht verloren.
    //
    // Die Tests sollen sowohl mit dem Axon Server als auch mit einer JPA Konfiguration ausgeführt
    // werden. Um das zu erreichen muss eine einheitliche Prüfung von Fehlern möglich sein. Dazu
    // gibt es zwei Möglichkeiten:
    //      1. Im Test wird immer der SimpleCommandBus verwendet, auch wenn der Test mit der
    //          Axon Server Konfiguration läuft. Ein Zugriff auf entfernte Command Handler ist
    //          nicht erforderlich, so dass der SimpleCommandBus ausreicht. Der SimpleCommandBus
    //          liefert Domain Exceptions unverändert an den Aufrufer.
    //      2. Alle auftretenden Exceptions werden in eine einheitliche auswertbare Form gebracht.
    //          Weil die Axon RemoteHandlingException den ursprünglichen Typ nicht weitergibt und
    //          kann nur die Fehlermeldung selbst geprüft werden.
    @Bean
    fun commandBus(txManager: TransactionManager, axonConfiguration: AxonConfiguration): CommandBus {

        val commandBus = SimpleCommandBus.builder()
            .transactionManager(txManager)
            .messageMonitor(axonConfiguration.messageMonitor<CommandMessage<*>>(CommandBus::class.java, "commandBus"))
            .build()

        commandBus.registerHandlerInterceptor(
            CorrelationDataInterceptor<CommandMessage<*>>(axonConfiguration.correlationDataProviders())
        )

        return commandBus
    }

    @Bean
    fun commandGatewayFactory(commandBus: CommandBus): CommandGatewayFactory {

        return CommandGatewayFactory.builder()
            .commandBus(commandBus)
            .build()
    }

    @Bean
    fun ticTacToeGateway(commandGatewayFactory: CommandGatewayFactory): TicTacToeGateway {
        return commandGatewayFactory
            .createGateway(TicTacToeGateway::class.java)
    }

    @Bean
    fun anwenderverzeichnisGateway(commandGatewayFactory: CommandGatewayFactory): AnwenderverzeichnisGateway {
        return commandGatewayFactory
            .createGateway(AnwenderverzeichnisGateway::class.java)
    }

    @Bean
    fun anwenderGateway(commandGatewayFactory: CommandGatewayFactory): AnwenderGateway {
        return commandGatewayFactory
            .createGateway(AnwenderGateway::class.java)
    }

    @Bean
    fun warteraumGateway(commandGatewayFactory: CommandGatewayFactory): WarteraumGateway {
        return commandGatewayFactory
            .createGateway(WarteraumGateway::class.java)
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
}