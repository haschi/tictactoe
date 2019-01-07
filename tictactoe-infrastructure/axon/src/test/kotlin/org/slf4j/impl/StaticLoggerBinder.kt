package org.slf4j.impl

import com.github.haschi.tictactoe.infrastructure.axon.backend.TestLoggerFactory
import org.slf4j.ILoggerFactory
import org.slf4j.spi.LoggerFactoryBinder

open class StaticLoggerBinder : LoggerFactoryBinder {
    override fun getLoggerFactory(): ILoggerFactory {
        return TestLoggerFactory()
    }

    override fun getLoggerFactoryClassStr(): String {
        return TestLoggerFactory::class.java.simpleName
    }

    open var REQUESTED_API_VERSION = "1.6.99"

    companion object {
        @JvmField
        var REQUESTED_API_VERSION = "1.6.99"

        @JvmStatic
        val singleton = StaticLoggerBinder()
    }
}