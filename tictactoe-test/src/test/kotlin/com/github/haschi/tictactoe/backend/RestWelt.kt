package com.github.haschi.tictactoe.backend

import org.springframework.stereotype.Component
import org.springframework.web.client.HttpServerErrorException
import java.net.URI

@Component
class RestWelt {
    var spiel = URI("#")
    lateinit var error: HttpServerErrorException
}