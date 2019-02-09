package com.github.haschi.tictactoe.backend.controller

import org.springframework.hateoas.ResourceSupport
import org.springframework.http.MediaType
import org.springframework.util.MimeType

class VndError(val message: String) : ResourceSupport() {
    companion object {
        @JvmStatic
        val ERROR_JSON_UTF8 = MediaType.asMediaType(MimeType.valueOf("application/vnd.error+json;charset=UTF-8"))

        const val ERROR_JSON_UTF8_VALUE = "application/vnd.error+json;charset=UTF-8"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as VndError

        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }

    override fun toString(): String {
        return "VndError(message='$message')"
    }
}