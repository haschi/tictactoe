package com.github.haschi.tictactoe.requirements.backend

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.github.haschi.tictactoe.requirements.backend",
        "com.github.haschi.tictactoe.application",
        "com.github.haschi.tictactoe.domain",
        "com.github.haschi.tictactoe.backend.controller",
        "com.github.haschi.tictactoe.backend.marshalling"]
)
class TestApplication
