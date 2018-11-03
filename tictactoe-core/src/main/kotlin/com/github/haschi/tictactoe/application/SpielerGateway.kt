package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.SucheZeichenAus
import java.util.concurrent.CompletableFuture

interface SpielerGateway {
    fun send(command: SucheZeichenAus): CompletableFuture<Void>
}