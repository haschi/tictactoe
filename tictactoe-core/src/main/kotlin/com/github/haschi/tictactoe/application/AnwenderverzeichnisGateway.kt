package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.*
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface AnwenderverzeichnisGateway {
    fun send(command: LegeAnwenderverzeichnisAn): CompletableFuture<Aggregatkennung>
    fun send(command: RegistriereAnwender): CompletableFuture<Void>
    fun send(command: WaehleZeichenAus): CompletableFuture<Void>
    fun send(command: RichteWarteraumEin): CompletableFuture<Void>
    fun send(command: BetreteWarteraum): CompletableFuture<Void>
    fun send(command: LegeMaximaleWartezeitFest): CompletableFuture<Void>
}