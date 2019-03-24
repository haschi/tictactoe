package com.github.haschi.tictactoe.requirements.backend

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
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@JsonTest
class SetzeZeichenIT @Autowired constructor(private val json: JacksonTester<SetzeZeichen>) {
    @Test
    fun `SetzeZeichen kann serialisiert werde`() {

        val aggregatkennung = Aggregatkennung(UUID.randomUUID())
        val anwenderId = Aggregatkennung()

        val command = SetzeZeichen(
            aggregatkennung,
            Spielzug(Spieler('X', "Matthias", anwenderId), Feld('B', 2))
        )

        assertThat(json.write(command))
            .isEqualToJson(
                """
                    {
                        "spielId" : "$aggregatkennung",
                        "spielzug" : {
                            "spieler" : {
                                "zeichen" : "X",
                                "anwender" : "Matthias",
                                "anwenderId" : "$anwenderId"
                            },
                            "feld" : {
                                "spalte" : "B",
                                "zeile" : 2
                            }
                        }
                    }
                """
            )
    }
}