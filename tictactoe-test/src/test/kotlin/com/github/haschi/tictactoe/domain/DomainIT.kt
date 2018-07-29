package com.github.haschi.tictactoe.domain

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/com/github/haschi/tictactoe/requirements"],
    plugin = ["progress"],
    tags = ["@domäne"],
    strict = true
)
class DomainIT
