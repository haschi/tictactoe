package com.github.haschi.tictactoe.domain.values

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FeldTest
{
    @ParameterizedTest
    @ValueSource(chars = ['A', 'B', 'C'])
    fun `Felder dürfen die Spalten A, B und C besitzen`(spalte: Char)
    {
        assertThatCode { Feld(spalte, 1)}
                .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @ValueSource(chars = ['D', 'a', ' ', '?'])
    fun `Felder dürfen keine andere Spalten besitzen`(spalte: Char)
    {
        assertThatCode { Feld(spalte, 1) }
                .isInstanceOf(UngültigesFeld::class.java)
                .hasMessage("Ungültiges Feld '${spalte}1'")
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3])
    fun `Felder dürfen die Zeilen 1, 2 und 3 besitzen`(zeile: Int)
    {
        assertThatCode { Feld('A', zeile) }
                .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 4, 99])
    fun `Felder dürfen keine anderen Zeilen besitzen`(zeile: Int)
    {
        assertThatCode { Feld('A', zeile) }
                .isInstanceOf(UngültigesFeld::class.java)
                .hasMessage("Ungültiges Feld 'A$zeile'")
    }
}