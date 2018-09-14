package com.github.haschi.tictactoe.domain.values

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@JsonTest
@DisplayName("Marshalling Aggregatkennung")
open class AggregatkennungIT @Autowired constructor(val json: JacksonTester<Aggregatkennung>) {

    @Test
    fun `Aggregatkennung wird wie ein String serialisiert`() {

        val aggregatkennung = Aggregatkennung(UUID.randomUUID())
        val s = json.write(aggregatkennung).json

        assertThat(json.write(aggregatkennung)).isEqualToJson(s)
    }
}