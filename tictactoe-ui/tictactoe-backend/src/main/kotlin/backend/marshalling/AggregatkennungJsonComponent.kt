package com.github.haschi.tictactoe.backend.marshalling

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.boot.jackson.JsonComponent

@JsonComponent
class AggregatkennungJsonComponent {

    class AggregatkennungDeserializer : JsonDeserializer<Aggregatkennung>() {
        override fun deserialize(parser: JsonParser, context: DeserializationContext): Aggregatkennung {
            val id = parser.valueAsString
            return Aggregatkennung(id)
        }
    }

    class AggregatkennungSerializer : JsonSerializer<Aggregatkennung>() {
        override fun serialize(
            aggregatkennung: Aggregatkennung,
            jsonGenerator: JsonGenerator,
            serializerProvider: SerializerProvider
        ) {
            jsonGenerator.writeString(aggregatkennung.id)
        }
    }
}