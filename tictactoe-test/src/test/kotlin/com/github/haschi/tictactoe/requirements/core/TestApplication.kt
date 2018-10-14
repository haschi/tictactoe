package com.github.haschi.tictactoe.requirements.core

import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(
    scanBasePackages = [
        "com.github.haschi.tictactoe.requirements.core",
        "com.github.haschi.tictactoe.application",
        "com.github.haschi.tictactoe.domain"]
)
class TestApplication
