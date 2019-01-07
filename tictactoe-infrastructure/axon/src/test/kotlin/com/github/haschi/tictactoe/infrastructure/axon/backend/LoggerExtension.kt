package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import org.slf4j.event.Level
import java.util.*
import kotlin.reflect.KClass

class LoggerExtension : BeforeTestExecutionCallback, AfterTestExecutionCallback {

    enum class ContextKey {
        LOGLEVEL, APPENDER
    }


    override fun beforeTestExecution(context: ExtensionContext) {
        val level = context.element
            .map { findAnnotation(it, LogLevel::class.java).map { it.level }.orElse(Level.TRACE) }

        val clazz = context.element
            .map { findAnnotation(it, LogLevel::class.java).map { it.type } }

        clazz.ifPresent { kk: Optional<KClass<*>> ->
            kk.ifPresent {
                println("Test mit Logging-Unterstützung für $level ($it)")
//                val appender = ListAppender<ILoggingEvent>()
//                // resetLoggingContext();
//                // addAppenderToLoggingSources();
//                val logger = LoggerFactory.getLogger(it.java) as Logger
//                LoggerFactory.getILoggerFactory().getLogger("sdf").
//                logger.addAppender(appender)
//                logger.setLevel(level)
//                appender.start();
            }
        }

        context.getStore(NAMESPACE).put(level, ContextKey.LOGLEVEL)
    }

    override fun afterTestExecution(p0: ExtensionContext) {
        println("After")
    }

    companion object {
        val NAMESPACE = ExtensionContext.Namespace.create("com", "github", "haschi", "tictactoe", "LoggingExtension")
    }
}
