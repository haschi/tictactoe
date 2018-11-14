package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.values.Spieler
import cucumber.api.Transformer

class SpielerConverter : Transformer<Spieler>() {
    override fun transform(zeichen: String): Spieler {
        return Spieler(zeichen[0], "")
    }
}
