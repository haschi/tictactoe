package com.github.haschi.tictactoe.requirements.core.testing

import cucumber.api.Transformer
import java.time.Duration
import java.util.regex.Pattern

class DurationConverter : Transformer<Duration>() {
    override fun transform(value: String): Duration {
        val pattern = Pattern.compile("^((?<wert>\\d+) (?<einheit>Sekunde(n*)|Millisekunde(n*)))$")
        val matcher = pattern.matcher(value)

        if (matcher.matches()) {
            return when (matcher.group("einheit")) {
                "Sekunden", "Sekunde" -> Duration.ofSeconds(matcher.group("wert").toLong())
                "Millisekunden", "Millisekunde" -> Duration.ofMillis(matcher.group("wert").toLong())
                else -> throw IllegalArgumentException(value)
            }
        }

        throw IllegalArgumentException(value)
    }
}