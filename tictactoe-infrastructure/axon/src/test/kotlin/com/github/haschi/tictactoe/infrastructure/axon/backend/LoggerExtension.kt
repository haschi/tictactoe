package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport.findAnnotation
import org.slf4j.LoggerFactory

class LoggerExtension : BeforeTestExecutionCallback, AfterTestExecutionCallback, ParameterResolver {

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return TestLogger::class.java.equals(parameterContext.parameter.type)
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {

        val store = extensionContext.getStore(NAMESPACE)
        return store[ContextKey.LOGGER]
    }

    enum class ContextKey {
        LOGLEVEL, LOGGER
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        context.element
            .ifPresent { element ->
                findAnnotation(element, LogLevel::class.java)
                    .ifPresent { logLevel ->
                        TestLoggerFactory.Konfiguration = logLevel.level
                        val logger = LoggerFactory.getLogger(logLevel.type.java)
                        context.getStore(NAMESPACE).put(ContextKey.LOGGER, logger)
                    }
            }
    }

    override fun afterTestExecution(context: ExtensionContext) {
    }

    companion object {
        val NAMESPACE: ExtensionContext.Namespace = ExtensionContext.Namespace.create(
            "com", "github", "haschi", "tictactoe", "LoggingExtension"
        )
    }
}
