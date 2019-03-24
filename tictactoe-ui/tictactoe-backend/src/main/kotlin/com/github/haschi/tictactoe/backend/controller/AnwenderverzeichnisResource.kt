package com.github.haschi.tictactoe.backend.controller

import com.github.haschi.tictactoe.domain.values.Aggregatkennung

data class AnwenderverzeichnisResource(val id: Aggregatkennung)

data class AnwenderverzeichnisCollection(val verzeichnisse: List<AnwenderverzeichnisResource>)