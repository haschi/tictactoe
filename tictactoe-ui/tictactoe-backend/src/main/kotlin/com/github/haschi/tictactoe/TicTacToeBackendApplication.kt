package com.github.haschi.tictactoe

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux

@SpringBootApplication(
    scanBasePackages = [
        "com.github.haschi.tictactoe.backend",
        "com.github.haschi.tictactoe.infrastructure",
        "com.github.haschi.tictactoe.application",
        "com.github.haschi.tictactoe.domain"]
)
@AutoConfigureWebFlux
class TicTacToeBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(TicTacToeBackendApplication::class.java, *args)
}