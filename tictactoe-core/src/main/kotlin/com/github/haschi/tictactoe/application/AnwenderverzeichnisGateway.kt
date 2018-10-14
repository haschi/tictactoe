package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.commands.RegistriereAnwender
import com.github.haschi.tictactoe.domain.commands.WaehleZeichenAus
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.messaging.annotation.MetaDataValue
import java.util.concurrent.CompletableFuture

interface AnwenderverzeichnisGateway {
    fun send(command: LegeAnwenderverzeichnisAn, @MetaDataValue("id") key: String): CompletableFuture<Aggregatkennung>
    fun send(command: RegistriereAnwender, @MetaDataValue("id") key: String): CompletableFuture<Void>
    fun send(command: WaehleZeichenAus, @MetaDataValue("id") key: String): CompletableFuture<Void>
}