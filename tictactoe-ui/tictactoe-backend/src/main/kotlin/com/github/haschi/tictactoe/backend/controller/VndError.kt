package com.github.haschi.tictactoe.backend.controller

import org.springframework.hateoas.ResourceSupport

class VndError(val message: String) : ResourceSupport()