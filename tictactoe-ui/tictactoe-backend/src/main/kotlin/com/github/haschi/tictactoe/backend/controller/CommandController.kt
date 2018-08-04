package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class CommandController(private val objectMapper: ObjectMapper, private val commandGateway: CommandGateway) {

    @RequestMapping(path = ["/api/command"], method = [RequestMethod.POST])
    fun verarbeiteCommand(@RequestBody message: HttpCommandMessage) {
        val klasse = Class.forName(message.type)
        val payload = objectMapper.convertValue(message.payload, klasse)

        commandGateway.sendAndWait<Aggregatkennung>(payload)
    }
}