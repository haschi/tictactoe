package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Aggregatkennung
import org.springframework.hateoas.Identifiable
import org.springframework.hateoas.core.Relation

@Relation(collectionRelation = "anwenderverzeichnisse")
data class AnwenderverzeichnisResource(private val id: Aggregatkennung) :
    Identifiable<String> {
    override fun getId(): String {
        return id.id
    }
}