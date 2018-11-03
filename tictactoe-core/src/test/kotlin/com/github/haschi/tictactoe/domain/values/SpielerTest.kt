package com.github.haschi.tictactoe.domain.values

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SpielerTest {
    @ParameterizedTest
    @ValueSource(chars = ['X', 'O', ' '])
    fun `Spieler dürfen die Zeichen X, O und Leerzeichen verwenden`(zeichen: Char) {
        assertThatCode { Spieler(zeichen, "") }
                .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @ValueSource(chars = ['A', 'a', '1', 'ä', '?'])
    fun `Spieler dürfen keine anderen Zeichen verwenden`(zeichen: Char) {
        assertThatCode { Spieler(zeichen, "") }
                .isInstanceOf(UnzulässigesZeichen::class.java)
    }

    @Test
    fun `Spieler erhalten Fehlermeldung, wenn falsches Zeichen verwendet wird`() {
        assertThatCode { Spieler('?', "") }
                .hasMessage("Unzulässiges Zeichen '?' für Spieler")
    }
}