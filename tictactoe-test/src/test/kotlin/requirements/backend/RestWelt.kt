package com.github.haschi.tictactoe.requirements.backend

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import java.net.URI

@Component
class RestWelt {
    var spiel = URI("#")
    //    lateinit var error: Throwable
//    lateinit var vndError: VndError
//    lateinit var status: HttpStatus
    lateinit var spec: ClientResponse
}