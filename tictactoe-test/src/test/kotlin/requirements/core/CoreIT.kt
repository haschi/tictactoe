package com.github.haschi.tictactoe.requirements.core

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/com/github/haschi/tictactoe/requirements"],
    plugin = ["progress"],
    tags = ["@core"],
    strict = true
)
class CoreIT
