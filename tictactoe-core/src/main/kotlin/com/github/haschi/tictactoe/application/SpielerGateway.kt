package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.SucheZeichenAus
import org.axonframework.messaging.annotation.MetaDataValue
import java.util.concurrent.CompletableFuture

interface SpielerGateway {
    fun send(command: SucheZeichenAus, @MetaDataValue("id") key: String): CompletableFuture<Void>
}