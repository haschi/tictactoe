package com.github.haschi.tictactoe.backend.controller

import org.springframework.hateoas.ResourceSupport
import org.springframework.http.MediaType
import org.springframework.util.MimeType

class VndError(val message: String) : ResourceSupport() {
    companion object {
        val mediaType = MediaType.asMediaType(MimeType.valueOf("application/vnd.error+json;charset=UTF-8"))
    }
}