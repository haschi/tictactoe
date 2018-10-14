package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.messaging.annotation.MetaDataValue
import java.util.concurrent.CompletableFuture

interface TicTacToeGateway {
    fun send(command: BeginneSpiel, @MetaDataValue("id") key: String): CompletableFuture<Aggregatkennung>

    @Throws(FeldBelegt::class)
    fun send(command: SetzeZeichen, @MetaDataValue("id") key: String): CompletableFuture<Void>

    fun send(command: RegistriereAnwender): CompletableFuture<Void>
}