package com.github.haschi.tictactoe.backend

import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Spielzug
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@JsonTest()
@SpringBootTest(classes = [(TicTacToeBackendApplication::class)])
class SetzteZeichenIT {
    @Autowired
    private lateinit var json: JacksonTester<SetzeZeichen>

    @Test
    fun `SetzeZeichen kann serialisiert werde`() {

        val aggregatkennung = Aggregatkennung()
        val command = SetzeZeichen(
            aggregatkennung,
            Spielzug(Spieler('X'), Feld('B', 2))
        )

        val erwartet = """
                {
                    "spielId" : "${aggregatkennung}",
                    "spielzug" : {
                        "spieler" : {
                            "zeichen" : "X"
                        },
                        "feld" : {
                            "spalte" : "B",
                            "zeile" : 2
                        }
                    }
                }
            """.trimIndent()

        assertThat(json.write(command))
            .isEqualToJson(
                erwartet
            )
    }
}