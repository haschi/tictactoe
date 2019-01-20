package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestFactory
import org.slf4j.Logger
import org.slf4j.event.Level
import org.slf4j.event.Level.*

@DisplayName("Angenommen ich habe einen TestLogger mit Loglevel INFO konfiguriert")
class TestLoggerTest {

    data class Testfall(
        val ebene: Level,
        val ereignis: ProtokollEintrag,
        val sichtbar: List<Level>,
        val protokollEintragGenerator: (logger: TestLogger) -> Unit
    )

    @TestFactory
    fun `Testfälle Protokoll-Eintrag erzeugen`(): List<DynamicTest> {
        val ausnahme = Throwable("Ausnahme")

        return values().flatMap {
            listOf(
                Testfall(
                    it,
                    ProtokollEintrag(INFO, "Fehlermeldung"),
                    listOf(INFO, DEBUG, TRACE)
                ) { it.info("Fehlermeldung") },

                Testfall(it, ProtokollEintrag(INFO, "Fehlermeldung Parameter 1"), listOf(INFO, DEBUG, TRACE)) {
                    it.info(
                        "Fehlermeldung {}",
                        "Parameter 1"
                    )
                },
                Testfall(
                    it,
                    ProtokollEintrag(
                        INFO,
                        "Fehlermeldung Parameter 1 und Parameter 2"
                    ),
                    listOf(INFO, DEBUG, TRACE)
                ) { it.info("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
                Testfall(
                    it,
                    ProtokollEintrag(
                        INFO,
                        "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                    ),
                    listOf(INFO, DEBUG, TRACE)
                ) { it.info("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
                Testfall(it, ProtokollEintrag(INFO, "Fehlermeldung mit Ausnahme"), listOf(INFO, DEBUG, TRACE)) {
                    it.info(
                        "Fehlermeldung mit Ausnahme",
                        ausnahme
                    )
                },

                Testfall(
                    it,
                    ProtokollEintrag(WARN, "Fehlermeldung"),
                    listOf(WARN, INFO, DEBUG, TRACE)
                ) { it.warn("Fehlermeldung") },
                Testfall(it, ProtokollEintrag(WARN, "Fehlermeldung Parameter 1"), listOf(WARN, INFO, DEBUG, TRACE)) {
                    it.warn(
                        "Fehlermeldung {}",
                        "Parameter 1"
                    )
                },
                Testfall(
                    it,
                    ProtokollEintrag(
                        WARN,
                        "Fehlermeldung Parameter 1 und Parameter 2"
                    ),
                    listOf(WARN, INFO, DEBUG, TRACE)
                ) { it.warn("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
                Testfall(
                    it,
                    ProtokollEintrag(
                        WARN,
                        "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                    ),
                    listOf(WARN, INFO, DEBUG, TRACE)
                ) { it.warn("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
                Testfall(it, ProtokollEintrag(WARN, "Fehlermeldung mit Ausnahme"), listOf(WARN, INFO, DEBUG, TRACE)) {
                    it.warn(
                        "Fehlermeldung mit Ausnahme",
                        ausnahme
                    )
                },

                Testfall(
                    it,
                    ProtokollEintrag(ERROR, "Fehlermeldung"),
                    listOf(ERROR, WARN, INFO, DEBUG, TRACE)
                ) { it.error("Fehlermeldung") },
                Testfall(
                    it,
                    ProtokollEintrag(ERROR, "Fehlermeldung Parameter 1"),
                    listOf(ERROR, WARN, INFO, DEBUG, TRACE)
                ) {
                    it.error(
                        "Fehlermeldung {}",
                        "Parameter 1"
                    )
                },
                Testfall(
                    it,
                    ProtokollEintrag(
                        ERROR,
                        "Fehlermeldung Parameter 1 und Parameter 2"
                    ),
                    listOf(ERROR, WARN, INFO, DEBUG, TRACE)
                ) { it.error("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
                Testfall(
                    it,
                    ProtokollEintrag(
                        ERROR,
                        "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                    ),
                    listOf(ERROR, WARN, INFO, DEBUG, TRACE)
                ) { it.error("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
                Testfall(
                    it,
                    ProtokollEintrag(ERROR, "Fehlermeldung mit Ausnahme"),
                    listOf(ERROR, WARN, INFO, DEBUG, TRACE)
                ) {
                    it.error(
                        "Fehlermeldung mit Ausnahme",
                        ausnahme
                    )

                },

                Testfall(
                    it,
                    ProtokollEintrag(DEBUG, "Fehlermeldung"),
                    listOf(DEBUG, TRACE)
                ) { it.debug("Fehlermeldung") },
                Testfall(it, ProtokollEintrag(DEBUG, "Fehlermeldung Parameter 1"), listOf(DEBUG, TRACE)) {
                    it.debug(
                        "Fehlermeldung {}",
                        "Parameter 1"
                    )
                },
                Testfall(
                    it,
                    ProtokollEintrag(
                        DEBUG,
                        "Fehlermeldung Parameter 1 und Parameter 2"
                    ),
                    listOf(DEBUG, TRACE)
                ) { it.debug("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
                Testfall(
                    it,
                    ProtokollEintrag(
                        DEBUG,
                        "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                    ),
                    listOf(DEBUG, TRACE)
                ) { it.debug("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
                Testfall(it, ProtokollEintrag(DEBUG, "Fehlermeldung mit Ausnahme"), listOf(DEBUG, TRACE)) {
                    it.debug(
                        "Fehlermeldung mit Ausnahme",
                        ausnahme
                    )
                },

                Testfall(
                    it,
                    ProtokollEintrag(TRACE, "Fehlermeldung"),
                    listOf(TRACE)
                ) { it.trace("Fehlermeldung") },
                Testfall(
                    it,
                    ProtokollEintrag(TRACE, "Fehlermeldung Parameter 1"),
                    listOf(TRACE)
                ) {
                    it.trace(
                        "Fehlermeldung {}",
                        "Parameter 1"
                    )
                },
                Testfall(
                    it,
                    ProtokollEintrag(
                        TRACE,
                        "Fehlermeldung Parameter 1 und Parameter 2"
                    ),
                    listOf(TRACE)
                ) { it.trace("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
                Testfall(
                    it,
                    ProtokollEintrag(
                        TRACE,
                        "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                    ),
                    listOf(TRACE)
                ) { it.trace("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
                Testfall(
                    it,
                    ProtokollEintrag(TRACE, "Fehlermeldung mit Ausnahme"),
                    listOf(TRACE)
                ) {
                    it.trace(
                        "Fehlermeldung mit Ausnahme",
                        ausnahme
                    )
                }
            )
        }
            .mapIndexed { index, testfall ->
                dynamicTest("$index ${testfall.ebene} ${testfall.ereignis}") {
                    val logger = TestLogger(testfall.ebene, "Protokoll-Name")
                    //logger.clear()
                    testfall.protokollEintragGenerator(logger)

                    if (testfall.sichtbar.contains(testfall.ebene)) {
                        assertThat(logger.events).containsExactly(testfall.ereignis)
                    } else {
                        assertThat(logger.events).doesNotContain(testfall.ereignis)
                    }
                }
            }
    }


    data class Testfall2(val level: Level, val istWahrFür: List<Level>, val action: (logger: Logger) -> Boolean)

    @Nested
    @DisplayName("isEnabled Test")
    inner class isXXXEnabledTest {

        @TestFactory
        fun prüfen(): List<DynamicTest> {
            return Level.values().flatMap { aktuellerLogLevel ->
                listOf(
                    Testfall2(aktuellerLogLevel, listOf(TRACE, DEBUG, INFO, WARN, ERROR)) { it.isErrorEnabled },
                    Testfall2(aktuellerLogLevel, listOf(TRACE, DEBUG, INFO, WARN)) { it.isWarnEnabled },
                    Testfall2(aktuellerLogLevel, listOf(TRACE, DEBUG, INFO)) { it.isInfoEnabled },
                    Testfall2(aktuellerLogLevel, listOf(TRACE, DEBUG)) { it.isDebugEnabled },
                    Testfall2(aktuellerLogLevel, listOf(TRACE)) { it.isTraceEnabled }
                )
            }.map { testfall: Testfall2 ->
                dynamicTest("testfall: $testfall") {
                    val logger = TestLogger(testfall.level, "Protokoll-Name")
                    assertThat(testfall.action(logger))
                        .isEqualTo(testfall.istWahrFür.contains(testfall.level))
                }
            }
        }
    }
}