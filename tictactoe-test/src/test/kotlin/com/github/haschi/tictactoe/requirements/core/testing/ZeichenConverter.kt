package com.github.haschi.tictactoe.requirements.core.testing

import com.github.haschi.tictactoe.domain.values.Zeichen
import cucumber.api.Transformer

class ZeichenConverter : Transformer<Zeichen>() {
    override fun transform(zeichen: String): Zeichen {
        return Zeichen(zeichen[0])
    }
}
