package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.events.FeldBelegt
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.MimeType
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class TicTacToeControllerAdvise : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FeldBelegt::class)
    fun fallsFeldBelegt(request: HttpServletRequest, ex: FeldBelegt): ResponseEntity<VndError> {

        val error = VndError(ex.message ?: "Unbekannter Fehler")
        val help = linkTo(SpielController::class).slash(ex.id.toString()).withRel("help")
        println(help)
        error.add(help)

        val appVendorJson = MediaType.asMediaType(MimeType.valueOf("application/vnd.error+json"))

        return ResponseEntity
            .unprocessableEntity()
            .contentType(appVendorJson)
            .body(error)
    }
}