package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.slf4j.event.Level
import org.slf4j.helpers.MarkerIgnoringBase

object TestLogger : MarkerIgnoringBase() {

    var level = Level.INFO

    override fun warn(p0: String?) {
    }

    override fun warn(p0: String?, p1: Any?) {
    }

    override fun warn(p0: String?, vararg p1: Any?) {
    }

    override fun warn(p0: String?, p1: Any?, p2: Any?) {
    }

    override fun warn(p0: String?, p1: Throwable?) {
    }

    override fun info(p0: String?) {
        if (isInfoEnabled)
            println(p0)
    }

    override fun info(p0: String?, p1: Any?) {
    }

    override fun info(p0: String?, p1: Any?, p2: Any?) {
    }

    override fun info(p0: String?, vararg p1: Any?) {
    }

    override fun info(p0: String?, p1: Throwable?) {
    }

    override fun isErrorEnabled(): Boolean {
        return level == Level.ERROR
    }

    override fun error(p0: String?) {
    }

    override fun error(p0: String?, p1: Any?) {
    }

    override fun error(p0: String?, p1: Any?, p2: Any?) {
    }

    override fun error(p0: String?, vararg p1: Any?) {
    }

    override fun error(p0: String?, p1: Throwable?) {
    }

    override fun isDebugEnabled(): Boolean {
        return level == Level.DEBUG
    }

    override fun debug(p0: String?) {
    }

    override fun debug(p0: String?, p1: Any?) {
    }

    override fun debug(p0: String?, p1: Any?, p2: Any?) {
    }

    override fun debug(p0: String?, vararg p1: Any?) {
    }

    override fun debug(p0: String?, p1: Throwable?) {
    }

    override fun isInfoEnabled(): Boolean {
        return level == Level.INFO
    }

    override fun trace(p0: String?) {
        if (isTraceEnabled)
            println(p0)
    }

    override fun trace(p0: String?, p1: Any?) {
    }

    override fun trace(p0: String?, p1: Any?, p2: Any?) {
    }

    override fun trace(p0: String?, vararg p1: Any?) {
    }

    override fun trace(p0: String?, p1: Throwable?) {
        if (isTraceEnabled)
            println(p0)
    }

    override fun isWarnEnabled(): Boolean {
        return level == Level.WARN
    }

    override fun isTraceEnabled(): Boolean {
        return level == Level.TRACE
    }
}