package com.github.haschi.tictactoe.requirements

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(plugin = ["progress"], tags = ["@domäne"])
class DomainIT
