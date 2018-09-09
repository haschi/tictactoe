package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.events.FeldBelegt
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@RequestMapping(produces = ["application/vnd.error+json"])
class TicTacToeControllerAdvise : ResponseEntityExceptionHandler() {

    @ExceptionHandler(FeldBelegt::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    fun fallsFeldBelegt(request: HttpServletRequest, ex: FeldBelegt): VndError {

//        val help = linkTo(SpielController::class).slash(ex.id.toString()).withRel("help")
//        error.add(help)
        return VndError(ex.message ?: "Unbekannter Fehler")
    }
}
