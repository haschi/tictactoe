package com.github.haschi.tictactoe.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TicTacToeBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(TicTacToeBackendApplication::class.java, *args)
}