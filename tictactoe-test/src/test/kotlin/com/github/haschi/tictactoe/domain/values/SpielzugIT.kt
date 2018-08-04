package com.github.haschi.tictactoe.domain.values

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@JsonTest
class SpielzugIT {
    @Autowired
    private lateinit var json: JacksonTester<Spielzug>

    @Test
    fun `Serialisierung eines Spielzugs klappt`() {
        val spielzug = Spielzug(Spieler('X'), Feld('A', 1))
        assertThat(json.write(spielzug))
            .isEqualToJson(
                """
                {
                    "spieler": {
                        "zeichen": "X"
                    },
                    "feld": {
                        "spalte": "A",
                        "zeile": 1
                    }
                }
            """.trimIndent()
            )
    }
}