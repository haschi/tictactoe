package com.github.haschi.tictactoe.requirements.core.values

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@JsonTest
@ActiveProfiles("test")
class SpielzugIT @Autowired constructor(private val json: JacksonTester<Spielzug>) {

    @Test
    fun `Serialisierung eines Spielzugs klappt`() {
        val anwenderId = Aggregatkennung()
        val spielzug = Spielzug(
            Spieler('X', "Matthias", anwenderId),
            Feld('A', 1)
        )
        assertThat(json.write(spielzug))
            .isEqualToJson(
                """
                {
                    "spieler": {
                        "zeichen": "X",
                        "anwender": "Matthias",
                        "anwenderId": "$anwenderId"
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