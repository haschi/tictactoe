package com.github.haschi.tictactoe.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.hateoas.config.EnableEntityLinks

@SpringBootApplication
@EnableEntityLinks
class TicTacToeBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(TicTacToeBackendApplication::class.java, *args)
}