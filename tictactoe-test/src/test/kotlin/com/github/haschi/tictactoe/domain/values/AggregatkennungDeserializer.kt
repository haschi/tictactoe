package com.github.haschi.tictactoe.domain.values

import gherkin.deps.com.google.gson.JsonDeserializationContext
import gherkin.deps.com.google.gson.JsonDeserializer
import gherkin.deps.com.google.gson.JsonElement
import org.springframework.boot.jackson.JsonComponent
import java.lang.reflect.Type

@JsonComponent
class AggregatkennungDeserializer : JsonDeserializer<Aggregatkennung> {
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        deserializationContext: JsonDeserializationContext
    ): Aggregatkennung {
        return Aggregatkennung(jsonElement.asString)
    }
}