package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.backend.controller.VndError.Companion.ERROR_JSON_UTF8
import com.github.haschi.tictactoe.domain.events.FeldBelegt
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@ControllerAdvice
class TicTacToeControllerAdvise {

    @ExceptionHandler(FeldBelegt::class)
    fun fallsFeldBelegt(ex: FeldBelegt): ResponseEntity<Mono<VndError>> {
        return ResponseEntity
            .badRequest()
            .contentType(ERROR_JSON_UTF8)
            .body(VndError(ex.message!!).toMono())
    }
//
//    @ExceptionHandler
//    fun fallsSpielerNichtAnDerReiheGewesen(
//        request: HttpServletRequest,
//        ex: SpielerNichtAndDerReiheGewesen
//    ): ResponseEntity<VndError> {
//        return Error(ex.message ?: "Unbekannter Fehler", HttpStatus.UNPROCESSABLE_ENTITY)
//    }
//
//    @ExceptionHandler(AxonNonTransientException::class)
//    fun falls(request: HttpServletRequest, ex: AxonNonTransientException): ResponseEntity<VndError> {
//        return Error(ex.message ?: "Unbekannter Fehler", HttpStatus.INTERNAL_SERVER_ERROR)
//    }
//}
}
