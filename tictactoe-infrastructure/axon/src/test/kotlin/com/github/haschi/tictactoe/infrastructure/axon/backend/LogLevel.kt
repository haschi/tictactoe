package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.event.Level
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
annotation class LogLevel(val level: Level, val type: KClass<*>)