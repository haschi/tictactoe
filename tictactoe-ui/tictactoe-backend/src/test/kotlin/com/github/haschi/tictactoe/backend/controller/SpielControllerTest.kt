package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.application.TicTacToeGateway
import org.axonframework.queryhandling.QueryGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@ExtendWith(SpringExtension::class)
@WebMvcTest(SpielController::class)
// @SpringBootTest
open class SpielControllerTest() {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var commandGateway: TicTacToeGateway

    @MockBean
    lateinit var queryGateway: QueryGateway

    @Test
    fun `Feld belegt führt zu Response mit Status 404`() {

    }
}