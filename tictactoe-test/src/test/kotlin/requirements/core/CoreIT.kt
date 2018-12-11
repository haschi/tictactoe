package com.github.haschi.tictactoe.requirements.core

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    glue = ["com.github.haschi.tictactoe.requirements.shared", "com.github.haschi.tictactoe.requirements.core"],
    features = ["classpath:com/github/haschi/tictactoe/requirements"],
    plugin = ["progress"],
    tags = ["@core"],
    strict = true
)
class CoreIT
