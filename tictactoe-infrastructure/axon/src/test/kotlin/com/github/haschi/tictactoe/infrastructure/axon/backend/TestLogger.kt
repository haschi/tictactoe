package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.event.Level
import org.slf4j.helpers.FormattingTuple
import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter

class TestLogger(var currentLogLevel: Level = Level.INFO, name: String) : MarkerIgnoringBase() {

    var events: List<ProtokollEintrag> = emptyList()

    init {
        this.name = name
    }

    override fun warn(message: String) {
        logMessage(Level.WARN, MessageFormatter.arrayFormat(message, emptyArray()))
    }

    override fun warn(message: String, argument1: Any?) {
        logMessage(Level.WARN, MessageFormatter.arrayFormat(message, arrayOf(argument1)))
    }

    override fun warn(message: String, vararg arguments: Any) {
        logMessage(Level.WARN, MessageFormatter.arrayFormat(message, arguments))
    }

    override fun warn(message: String, argument1: Any?, argument2: Any?) {
        logMessage(Level.WARN, MessageFormatter.arrayFormat(message, arrayOf(argument1, argument2)))
    }

    override fun warn(message: String, throwable: Throwable) {
        logMessage(Level.WARN, MessageFormatter.arrayFormat(message, emptyArray(), throwable))
    }

    override fun info(message: String) {
        logMessage(Level.INFO, FormattingTuple(message))
    }

    override fun info(message: String, argument1: Any) {
        logMessage(Level.INFO, MessageFormatter.format(message, argument1))
    }

    override fun info(message: String, argument1: Any, argument2: Any) {
        logMessage(Level.INFO, MessageFormatter.format(message, argument1, argument2))
    }

    override fun info(message: String, vararg arguments: Any) {
        logMessage(Level.INFO, MessageFormatter.arrayFormat(message, arguments))
    }

    override fun info(message: String, throwable: Throwable) {
        logMessage(Level.INFO, MessageFormatter.arrayFormat(message, emptyArray(), throwable))
    }

    override fun isErrorEnabled(): Boolean {
        return isLevelEnabled(Level.ERROR)
    }

    override fun error(message: String) {
        logMessage(Level.ERROR, MessageFormatter.arrayFormat(message, emptyArray()))
    }

    override fun error(message: String, argument: Any) {
        logMessage(Level.ERROR, MessageFormatter.arrayFormat(message, arrayOf(argument)))
    }

    private fun logMessage(level: Level, format: FormattingTuple) {
        if (isLevelEnabled(level)) {
            events += ProtokollEintrag(level, format.message)
        }
    }

    private fun isLevelEnabled(level: Level) = level.toInt() >= this.currentLogLevel.toInt()

    override fun error(message: String, argument1: Any, argument2: Any) {
        logMessage(Level.ERROR, MessageFormatter.arrayFormat(message, arrayOf(argument1, argument2)))
    }

    override fun error(message: String, vararg arguments: Any) {
        logMessage(Level.ERROR, MessageFormatter.arrayFormat(message, arguments))
    }

    override fun error(message: String, throwable: Throwable) {
        logMessage(Level.ERROR, MessageFormatter.arrayFormat(message, emptyArray(), throwable))
    }

    override fun isDebugEnabled(): Boolean {
        return isLevelEnabled(Level.DEBUG)
    }

    override fun debug(message: String) {
        logMessage(Level.DEBUG, MessageFormatter.arrayFormat(message, emptyArray()))
    }

    override fun debug(message: String, argument: Any?) {
        logMessage(Level.DEBUG, MessageFormatter.arrayFormat(message, arrayOf(argument)))
    }

    override fun debug(message: String, argument1: Any?, argument2: Any?) {
        logMessage(Level.DEBUG, MessageFormatter.arrayFormat(message, arrayOf(argument1, argument2)))
    }

    override fun debug(message: String, vararg arguments: Any?) {
        logMessage(Level.DEBUG, MessageFormatter.arrayFormat(message, arguments))
    }

    override fun debug(message: String, throwable: Throwable?) {
        logMessage(Level.DEBUG, MessageFormatter.arrayFormat(message, emptyArray(), throwable))
    }

    override fun isInfoEnabled(): Boolean {
        return isLevelEnabled(Level.INFO)
    }

    override fun trace(message: String) {
        logMessage(Level.TRACE, MessageFormatter.arrayFormat(message, emptyArray()))
    }

    override fun trace(message: String, argument: Any?) {
        logMessage(Level.TRACE, MessageFormatter.arrayFormat(message, arrayOf(argument)))
    }

    override fun trace(message: String, argument1: Any?, argument2: Any?) {
        logMessage(Level.TRACE, MessageFormatter.arrayFormat(message, arrayOf(argument1, argument2)))
    }

    override fun trace(message: String, vararg arguments: Any?) {
        logMessage(Level.TRACE, MessageFormatter.arrayFormat(message, arguments))
    }

    override fun trace(message: String, throwable: Throwable?) {
        logMessage(Level.TRACE, MessageFormatter.arrayFormat(message, emptyArray(), throwable))
    }

    override fun isWarnEnabled(): Boolean {
        return isLevelEnabled(Level.WARN)
    }

    override fun isTraceEnabled(): Boolean {
        return isLevelEnabled(Level.TRACE)
    }

    fun clear() {
        events = emptyList()
    }
}