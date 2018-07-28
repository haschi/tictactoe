package com.github.haschi.tictactoe.domain.values

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent

@JsonComponent
class AggregatkennungSerializer : JsonSerializer<Aggregatkennung>() {
    override fun serialize(
        aggregatkennung: Aggregatkennung,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeString(aggregatkennung.id)
    }
}