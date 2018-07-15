package com.github.haschi.tictactoe.requirements

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class AusführungsumgebungPrüfenIT
{
    @Test
    @Disabled
    fun `Systemeigenschaft 'ObjectFactory' muss verfügbar sein`() {

        val factory = System.getProperty("cucumber.api.java.ObjectFactory")

        assertThat(factory)
                .`as`("Systemeigenschaft 'cucumber.api.java.ObjectFactory' muss gesetzt sein")
                .isNotBlank()
    }
}