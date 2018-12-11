package com.github.haschi.tictactoe.requirements.backend.testing

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
@EnableAutoConfiguration
class TestKonfiguration(private val objectMapper: ObjectMapper) {

    @Bean
    fun mappingJacksonHttpMessageConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = objectMapper
        return converter
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}
