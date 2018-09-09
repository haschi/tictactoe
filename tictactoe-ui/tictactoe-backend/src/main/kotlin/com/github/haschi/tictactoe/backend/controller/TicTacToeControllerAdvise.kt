package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.events.FeldBelegt
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class TicTacToeControllerAdvise : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FeldBelegt::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun fallsFeldBelegt(request: HttpServletRequest, ex: FeldBelegt): ResponseEntity<VndError> {

//        val help = linkTo(SpielController::class).slash(ex.id.toString()).withRel("help")
//        error.add(help)

        return ResponseEntity
            .unprocessableEntity()
            .error(ex.message ?: "Unbekannter Fehler")
    }
}

private fun ResponseEntity.BodyBuilder.error(fehlermeldung: String): ResponseEntity<VndError> {
    return this
        .contentType(VndError.mediaType)
        .body(VndError(fehlermeldung))
}
