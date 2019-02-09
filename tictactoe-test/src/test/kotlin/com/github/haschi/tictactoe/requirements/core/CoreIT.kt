package com.github.haschi.tictactoe.requirements.core

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    extraGlue = ["com.github.haschi.tictactoe.requirements.shared"],
    features = ["classpath:com/github/haschi/tictactoe/requirements"],
    plugin = ["progress"],
    tags = ["@core"],
    strict = true
)
class CoreIT
