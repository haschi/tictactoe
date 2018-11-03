package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.*
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface AnwenderverzeichnisGateway {
    fun send(command: LegeAnwenderverzeichnisAn): CompletableFuture<Aggregatkennung>
    fun send(command: RegistriereAnwender): CompletableFuture<Void>
    fun send(command: WaehleZeichenAus): CompletableFuture<Void>
    fun send(command: RichteDatingRoomEin): CompletableFuture<Void>
    fun send(command: BetreteDatingRoom): CompletableFuture<Void>
}