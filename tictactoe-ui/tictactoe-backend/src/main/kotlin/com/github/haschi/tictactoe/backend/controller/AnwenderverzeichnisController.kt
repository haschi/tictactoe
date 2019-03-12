package com.github.haschi.tictactoe.backend.controller

import org.springframework.hateoas.ExposesResourceFor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/anwenderverzeichnisse")
@ExposesResourceFor(AnwenderverzeichnisResource::class)
class AnwenderverzeichnisController

