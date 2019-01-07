package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class TestLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String?): Logger {
        return TestLogger
    }
}