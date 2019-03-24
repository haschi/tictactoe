package com.github.haschi.tictactoe.backend

import com.github.haschi.tictactoe.application.AnwenderverzeichnisGateway
import com.github.haschi.tictactoe.domain.commands.LegeAnwenderverzeichnisAn
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class TicTacToeBackendApplicationTest @Autowired constructor(private val anwenderverzeichnisGateway: AnwenderverzeichnisGateway) {
    @Test
    fun `Anwendung kann gestartet werden`() {
        anwenderverzeichnisGateway.send(
            LegeAnwenderverzeichnisAn(Aggregatkennung("8d51480f-6309-42d9-986b-3685a362795a"))
        )
            .get()
    }
}