package com.github.haschi.tictactoe.backend.marshalling

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.haschi.tictactoe.backend.controller.VndError
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Component

@Component
class CodecsConfiguration(private val mapper: ObjectMapper) {
    @Bean
    fun jackson2JsonDecoder(): Jackson2JsonDecoder =
        Jackson2JsonDecoder(
            mapper,
            MediaType.APPLICATION_STREAM_JSON,
            MediaType.APPLICATION_JSON,
            VndError.ERROR_JSON_UTF8
        )

    @Bean
    fun jackson2Encoder(): Jackson2JsonEncoder = Jackson2JsonEncoder(
        mapper,
        MediaType.APPLICATION_STREAM_JSON,
        MediaType.APPLICATION_JSON,
        VndError.ERROR_JSON_UTF8
    )
}