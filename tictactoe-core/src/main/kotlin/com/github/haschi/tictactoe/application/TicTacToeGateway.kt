package com.github.haschi.tictactoe.application

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.values.Aggregatkennung

interface TicTacToeGateway
{
    fun sendAndWait(command: BeginneSpiel): Aggregatkennung
}