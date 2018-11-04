package com.github.haschi.tictactoe.requirements.core.testing

import cucumber.api.Transformer
import java.time.Duration

class DurationConverter : Transformer<Duration>() {
    override fun transform(value: String): Duration {
        return Duration.ofMillis(value.toLong())
    }
}