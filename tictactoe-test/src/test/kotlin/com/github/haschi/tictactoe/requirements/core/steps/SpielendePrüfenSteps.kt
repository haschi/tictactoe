package com.github.haschi.tictactoe.requirements.core.steps

import com.github.haschi.tictactoe.domain.commands.BeginneSpiel
import com.github.haschi.tictactoe.domain.commands.SetzeZeichen
import com.github.haschi.tictactoe.domain.events.SpielGewonnen
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.requirements.core.testing.DieWelt
import com.github.haschi.tictactoe.requirements.shared.testing.Resolver
import com.github.haschi.tictactoe.requirements.shared.testing.SpielzugGenerator
import cucumber.api.java.de.Angenommen
import cucumber.api.java.de.Dann
import org.assertj.core.api.Assertions.assertThat
import java.util.*

class SpielendePrüfenSteps(private val welt: DieWelt) {
    @Angenommen("^ich habe folgenden Spielverlauf:$")
    fun `ich habe folgenden Spielverlauf`(spielverlauf: List<SpielzugGenerator>) {
        welt.compose {
            welt.tictactoe.send(
                BeginneSpiel(
                    Aggregatkennung(UUID.randomUUID()),
                    Spieler('X', "Matthias", Aggregatkennung()),
                    Spieler('O', "Martin", Aggregatkennung())
                )
            )
                .thenApply { copy(spielId = it) }
        }

        spielverlauf.forEach {
            welt.compose {
                welt.tictactoe.send(SetzeZeichen(spielId, it.auflösen(this)))
                    .thenApply { this }
            }
        }
    }

    @Dann("hat Spieler {spieler} gewonnen")
    fun `hat Spieler gewonnen`(spieler: Resolver<Spieler>) {
        welt.join {
            assertThat(zustand.ereignisse)
                .contains(SpielGewonnen(zustand.spielId, spieler.resolve(zustand)))
        }
    }

    @Dann("hat Spieler {spieler} nicht gewonnen")
    fun `hat Spieler nicht gewonnen`(spieler: Resolver<Spieler>) {
        welt.join {
            assertThat(zustand.ereignisse)
                .doesNotContain(SpielGewonnen(zustand.spielId, spieler.resolve(zustand)))
        }
    }
}
