package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import java.util.concurrent.CompletableFuture

interface TicTacToeGateway {
    fun send(command: BeginneSpiel): CompletableFuture<Aggregatkennung>
    fun send(command: SetzeZeichen): CompletableFuture<Unit>
}