package com.github.haschi.tictactoe.backend.controller

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.github.haschi.tictactoe.domain.values.Spielfeld

//import org.springframework.hateoas.ResourceSupport

class SpielfeldResource(
    @JsonUnwrapped val spielfeld: Spielfeld
)
//    : ResourceSupport()