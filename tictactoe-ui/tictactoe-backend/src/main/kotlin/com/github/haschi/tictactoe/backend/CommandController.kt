package com.github.haschi.tictactoe.backend

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class CommandController {
    @RequestMapping(path = ["/api/command"], method = [RequestMethod.POST])
    fun verarbeiteCommand() {
        println("verarbeite Command")
    }
}