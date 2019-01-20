package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.event.Level

data class ProtokollEintrag(val level: Level, val message: String)