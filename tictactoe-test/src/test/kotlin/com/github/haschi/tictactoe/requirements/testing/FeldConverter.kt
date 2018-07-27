package com.github.haschi.tictactoe.requirements.testing

import com.github.haschi.tictactoe.domain.values.Feld
import cucumber.api.Transformer

class FeldConverter : Transformer<Feld>() {
    override fun transform(feld: String): Feld {
        val regex = Regex("(?<spalte>[ABC])(?<zeile>[123])")
        val match = regex.find(feld)
        val spalte = match?.groups?.get("spalte")?.value!![0]
        val zeile = match.groups["zeile"]?.value?.toInt()!!

        return Feld(spalte, zeile)
    }
}
