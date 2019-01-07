package com.github.haschi.tictactoe.infrastructure.axon.backend

import mu.KLogging
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException
import org.springframework.hateoas.VndErrors
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class AxonServerExceptionAdvice(val logrefFactory: LogrefFactory) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AxonServerRemoteCommandHandlingException::class)
    fun falls(
        request: HttpServletRequest,
        ex: AxonServerRemoteCommandHandlingException
    ): ResponseEntity<VndErrors.VndError> {
        logger.warn("Domänen-Fehler", ex)
        val descriptions = ex.exceptionDescriptions.joinToString(separator = "; ")
        val error = VndErrors.VndError(logrefFactory.nächsteId(), descriptions)
        return ResponseEntity(error, HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    companion object : KLogging()
}