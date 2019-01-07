package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UuidLogrefFactoryTest {

    @Test
    fun `Logref Factory generiert stets neue Identifier`() {
        val factory = UuidLogrefFactory()

        assertThat(factory.nächsteId())
            .isNotEqualTo(factory.nächsteId())
    }
}