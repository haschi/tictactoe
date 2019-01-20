package com.github.haschi.tictactoe.infrastructure.axon.backend

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.axoniq.axonserver.grpc.ErrorMessage
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.axonserver.connector.ErrorCode
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.event.Level
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.hateoas.MediaTypes
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.concurrent.CompletableFuture

@ExtendWith(SpringExtension::class)
@WebMvcTest(TestController::class)
@ContextConfiguration()
@DisplayName("Wenn ich einen Domänen-Fehler erkenne")
class AxonServerExceptionAdviceTest(@Autowired private val mvc: MockMvc) {

    @MockBean
    private lateinit var command: CommandGateway

    @MockBean
    private lateinit var logrefFactory: LogrefFactory

    private val fehlermeldung = "Ein Fehler ist aufgetreten"
    private val logref = "12345"

    private val errorMessage: ErrorMessage = ErrorMessage.newBuilder()
        .setMessage("Fehlermeldung")
        .setLocation("dev3")
        .addDetails(fehlermeldung)
        .build()

    private val future = CompletableFuture<Any>()

    private lateinit var result: MvcResult

    @BeforeEach
    fun `HTTP Request vorbereiten`() {
        future.completeExceptionally(
            AxonServerRemoteCommandHandlingException(
                ErrorCode.COMMAND_EXECUTION_ERROR.errorCode(),
                errorMessage
            )
        )

        whenever(command.send<Any>(any()))
            .thenReturn(future)

        whenever(logrefFactory.nächsteId())
            .thenReturn(logref)

        result = mvc.perform(MockMvcRequestBuilders.post("/api/test"))
            .andExpect(
                MockMvcResultMatchers.request()
                    .asyncStarted()
            )
            .andReturn()
    }

    @Nested
    @DisplayName("dann gebe ich einen Herstellerfehler zurück")
    inner class HerstellerfehlerTesten {
        @Test
        fun `der den Media Type application hal+json charset=UTF-8 besitzt`() {
            mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaTypes.HAL_JSON_UTF8))
        }

        @Test
        fun `der eine Referenz auf den Eintrag im Fehler-Protokoll enthält`() {
            mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logref").value(logref))
        }

        @Test
        fun `der die ursprüngliche Beschreibung des Domänen-Fehlers enthält`() {
            mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(fehlermeldung))
        }
    }

    @Test
    fun `dann gebe ich den HTTP Status 422 Unprocessable Entity zurück`() {
        mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
    }

    @Nested
    @DisplayName("dann finde ich im Fehler-Protokoll")
    @ExtendWith(LoggerExtension::class)
    inner class LoggingTest {

        @Test
        @LogLevel(level = Level.TRACE, type = AxonServerExceptionAdvice::class)
        fun `einen Eintrag mit der Beschreibung des Domänen-Fehlers`(logger: TestLogger) {
            mvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)

            assertThat(logger.events).contains(
                ProtokollEintrag(Level.WARN, "Domänen-Fehler")
            )
        }
    }
}