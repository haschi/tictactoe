package com.github.haschi.tictactoe.infrastructure.axon.backend

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.axoniq.axonserver.grpc.ErrorMessage
import org.axonframework.axonserver.connector.ErrorCode
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebMvcTest(TestController::class)
@ContextConfiguration()
class AxonServerExceptionAdviceTest(@Autowired private val mvc: MockMvc) {

    @MockBean
    private
    lateinit var command: CommandGateway

    @Test
    fun `AxonServerExceptionAdvice wandelt Domain Exception HTTP Status Unprocessable Entity`() {

        val errorMessage = ErrorMessage.newBuilder()
            .setMessage("Fehlermeldung")
            .setLocation("dev3")
            .addDetails("Ein Fehler ist aufgetreten")
            .build()

        ErrorCode.COMMAND_EXECUTION_ERROR.convert(IllegalArgumentException(""))
        val future = CompletableFuture<Any>()
        future.completeExceptionally(
            AxonServerRemoteCommandHandlingException(
                ErrorCode.COMMAND_EXECUTION_ERROR.errorCode(),
                errorMessage
            )
        )

        whenever(command.send<Any>(any()))
            .thenReturn(future)

        val request = MockMvcRequestBuilders.post("/api/test")

        val result = mvc.perform(request)
            .andExpect(
                MockMvcResultMatchers.request()
                    .asyncStarted()
            )
            .andReturn()

        mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaTypes.HAL_JSON_UTF8))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                        "message": "Ein Fehler ist aufgetreten",
                        "logref": "Referenz"
                    }
                """.trimIndent()
                )
            )
    }
}