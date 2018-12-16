package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.BetreteWarteraum
import com.github.haschi.tictactoe.domain.commands.LegeMaximaleWartezeitFest
import com.github.haschi.tictactoe.domain.commands.RichteWarteraumEin
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface WarteraumGateway {
    fun send(command: RichteWarteraumEin): CompletableFuture<Aggregatkennung>
    fun send(command: BetreteWarteraum): CompletableFuture<Void>
    fun send(command: LegeMaximaleWartezeitFest): CompletableFuture<Void>
}