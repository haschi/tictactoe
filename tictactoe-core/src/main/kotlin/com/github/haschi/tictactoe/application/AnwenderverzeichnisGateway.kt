package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface AnwenderverzeichnisGateway {
    fun send(command: LegeAnwenderverzeichnisAn): CompletableFuture<Aggregatkennung>
    fun send(command: RegistriereAnwender): CompletableFuture<Void>
    fun send(command: WaehleZeichenAus): CompletableFuture<Void>
}