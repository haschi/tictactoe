package com.github.haschi.tictactoe.requirements.core.testing

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.Duration

class DurationConverterTest {

    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @CsvSource(
        value = [
            "10 Sekunden, PT10s",
            "1 Sekunde, PT1s",
            "10 Millisekunden, PT0.010s",
            "10 Millisekunde, PT0.010s",
            "1 Millisekunden, PT0.001s",
            "1 Millisekunde, PT0.001s"
        ]
    )
    fun `Transformer transformiert Sekunden`(
        testwert: String,
        @ConvertWith(ZeitraumConverter::class) ergebnis: Duration
    ) {
        val converter = DurationConverter()

        assertThat(converter.transform(testwert))
            .isEqualTo(ergebnis)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "10", "Sekunden", "10  Millisekunden"])
    fun `Transformation schlägt bei falschen Werten fehl`(testwert: String) {
        val converter = DurationConverter()
        assertThatIllegalArgumentException().isThrownBy {
            converter.transform(testwert)
        }
    }

    private class ZeitraumConverter : ArgumentConverter {
        override fun convert(value: Any, context: ParameterContext): Any {
            return Duration.parse(value.toString())
        }
    }
}