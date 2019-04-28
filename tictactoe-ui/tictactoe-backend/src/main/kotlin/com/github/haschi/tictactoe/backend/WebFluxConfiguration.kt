package com.github.haschi.tictactoe.backend

import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebFluxConfiguration(private val encoder: Jackson2JsonEncoder, private val decoder: Jackson2JsonDecoder) :
    WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(encoder)
        configurer.defaultCodecs().jackson2JsonDecoder(decoder)
    }
}