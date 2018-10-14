package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.backend.controller.VndError.Companion.ERROR_JSON_UTF8_VALUE
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import org.axonframework.common.AxonNonTransientException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class TicTacToeControllerAdvise : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FeldBelegt::class)
    fun fallsFeldBelegt(request: HttpServletRequest, ex: FeldBelegt): ResponseEntity<VndError> {
        return error(ex.message ?: "Unbekannter Fehler", HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(AxonNonTransientException::class)
    fun falls(request: HttpServletRequest, ex: AxonNonTransientException): ResponseEntity<VndError> {
        return error(ex.message ?: "Unbekannter Fehler", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun error(message: String, status: HttpStatus): ResponseEntity<VndError> {
        val headers = HttpHeaders()
        headers.add("Content-Type", ERROR_JSON_UTF8_VALUE)

        return ResponseEntity(
            VndError(message),
            headers,
            status
        )
    }
}
