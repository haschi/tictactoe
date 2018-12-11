package com.github.haschi.tictactoe.requirements.backend

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    glue = ["com.github.haschi.tictactoe.requirements.shared", "com.github.haschi.tictactoe.requirements.backend"],
    features = ["classpath:com/github/haschi/tictactoe/requirements"],
    plugin = ["progress"],
    tags = ["@backend"],
    strict = true
)
class BackendIT