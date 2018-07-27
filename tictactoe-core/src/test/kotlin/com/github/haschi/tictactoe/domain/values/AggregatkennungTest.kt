package com.github.haschi.tictactoe.domain.values

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URI
import java.util.*

class AggregatkennungTest {
    @Test
    fun `Jede neue Aggregatkennung ist verschieden`() {

        assertThat(Aggregatkennung()).isNotEqualTo(Aggregatkennung())
    }

    @Test
    fun `Eine Aggregatkennung kann aus UUID gebildet werden`() {
        val id = UUID.randomUUID()

        assertThat(Aggregatkennung(id)).isEqualTo(Aggregatkennung(id))
    }

    @Test
    fun `Eine Aggregatkennung aus URI gebildet werden`() {
        val uri = URI.create("isbn:4711")
        assertThat(Aggregatkennung(uri)).isEqualTo(Aggregatkennung(uri))
        assertThat(Aggregatkennung(uri).toString()).isEqualTo(uri.toString())
    }
}