package com.github.haschi.tictactoe.requirements

import cucumber.api.PendingException
import cucumber.api.java.de.Angenommen
import com.github.haschi.tictactoe.domain.values.Spieler
import com.github.haschi.tictactoe.domain.values.Feld
import com.github.haschi.tictactoe.requirements.testing.FeldConverter
import com.github.haschi.tictactoe.requirements.testing.SpielerConverter
import cucumber.api.Transform

class SpielendePruefen
{
    @Angenommen("^ich habe folgenden Spielverlauf:$")
    fun ich_habe_folgenden_Spielverlauf(arg1: List<Spielzug>)
    {
        arg1.forEach{
            println("Spielzug ${it.spieler} - ${it.feld}")
        }
        println(arg1)
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc).
        // Field names for YourType must match the column names in
        // your feature file (except for spaces and capitalization).
        throw PendingException()
    }
}

class Spielzug(
        @Transform(SpielerConverter::class) val spieler: Spieler,
        @Transform(FeldConverter::class) val feld: Feld)
