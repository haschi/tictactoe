package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.event.Level

class TestLoggerFactory : ILoggerFactory {

    override fun getLogger(name: String): Logger {
        TODO("Jetzt nicht")
//        if (name == AxonServerExceptionAdvice::class.qualifiedName) {
//            if (!logger.containsKey(name)) {
//                logger += name to TestLogger(Konfiguration, name)
//            }
//        } else {
//            if (!logger.containsKey("Anderer Logger")) {
//                logger += "Anderer Logger" to TestLogger(Konfiguration, "Anderer Logger")
//            }
//        }
//
//        return logger[if (name == AxonServerExceptionAdvice::class.qualifiedName) name else "Anderer Logger"]!!
    }

    companion object {
        var logger: Map<String, TestLogger> = emptyMap()

        var Konfiguration = Level.INFO
    }
}