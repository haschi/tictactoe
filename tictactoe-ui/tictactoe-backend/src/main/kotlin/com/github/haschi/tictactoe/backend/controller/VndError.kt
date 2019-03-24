package com.github.haschi.tictactoe.backend.controller

import org.springframework.http.MediaType
import org.springframework.util.MimeType

data class VndError(val message: String) {
    companion object {
        @JvmStatic
        val ERROR_JSON_UTF8 = MediaType.asMediaType(MimeType.valueOf("application/vnd.error+json;charset=UTF-8"))

        const val ERROR_JSON_UTF8_VALUE = "application/vnd.error+json;charset=UTF-8"
    }
}