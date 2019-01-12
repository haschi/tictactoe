package com.github.haschi.tictactoe.infrastructure.axon.backend

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.slf4j.event.Level.*

@DisplayName("Angenommen ich habe einen TestLogger mit Loglevel INFO konfiguriert")
class TestLoggerTest {

    private val logger = TestLogger

    data class Testfall(
        val ereignis: TestLogger.Event,
        val protokollEintragGenerator: (logger: TestLogger) -> Unit
    )

    @TestFactory
    fun testfälleAusführen(): List<DynamicTest> {
        val ausnahme = Throwable("Ausnahme")
        return listOf(
            Testfall(TestLogger.Event(INFO, "Fehlermeldung")) { it.info("Fehlermeldung") },
            Testfall(TestLogger.Event(INFO, "Fehlermeldung Parameter 1")) {
                it.info(
                    "Fehlermeldung {}",
                    "Parameter 1"
                )
            },
            Testfall(
                TestLogger.Event(
                    INFO,
                    "Fehlermeldung Parameter 1 und Parameter 2"
                )
            ) { it.info("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
            Testfall(
                TestLogger.Event(
                    INFO,
                    "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                )
            ) { it.info("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
            Testfall(TestLogger.Event(INFO, "Fehlermeldung mit Ausnahme")) {
                it.info(
                    "Fehlermeldung mit Ausnahme",
                    ausnahme
                )
            },

            Testfall(TestLogger.Event(WARN, "Fehlermeldung")) { it.warn("Fehlermeldung") },
            Testfall(TestLogger.Event(WARN, "Fehlermeldung Parameter 1")) {
                it.warn(
                    "Fehlermeldung {}",
                    "Parameter 1"
                )
            },
            Testfall(
                TestLogger.Event(
                    WARN,
                    "Fehlermeldung Parameter 1 und Parameter 2"
                )
            ) { it.warn("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
            Testfall(
                TestLogger.Event(
                    WARN,
                    "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                )
            ) { it.warn("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
            Testfall(TestLogger.Event(WARN, "Fehlermeldung mit Ausnahme")) {
                it.warn(
                    "Fehlermeldung mit Ausnahme",
                    ausnahme
                )
            },

            Testfall(TestLogger.Event(ERROR, "Fehlermeldung")) { it.error("Fehlermeldung") },
            Testfall(TestLogger.Event(ERROR, "Fehlermeldung Parameter 1")) {
                it.error(
                    "Fehlermeldung {}",
                    "Parameter 1"
                )
            },
            Testfall(
                TestLogger.Event(
                    ERROR,
                    "Fehlermeldung Parameter 1 und Parameter 2"
                )
            ) { it.error("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
            Testfall(
                TestLogger.Event(
                    ERROR,
                    "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                )
            ) { it.error("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
            Testfall(TestLogger.Event(ERROR, "Fehlermeldung mit Ausnahme")) {
                it.error(
                    "Fehlermeldung mit Ausnahme",
                    ausnahme
                )
            },

            Testfall(TestLogger.Event(DEBUG, "Fehlermeldung")) { it.debug("Fehlermeldung") },
            Testfall(TestLogger.Event(DEBUG, "Fehlermeldung Parameter 1")) {
                it.debug(
                    "Fehlermeldung {}",
                    "Parameter 1"
                )
            },
            Testfall(
                TestLogger.Event(
                    DEBUG,
                    "Fehlermeldung Parameter 1 und Parameter 2"
                )
            ) { it.debug("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
            Testfall(
                TestLogger.Event(
                    DEBUG,
                    "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                )
            ) { it.debug("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
            Testfall(TestLogger.Event(DEBUG, "Fehlermeldung mit Ausnahme")) {
                it.debug(
                    "Fehlermeldung mit Ausnahme",
                    ausnahme
                )
            },

            Testfall(TestLogger.Event(TRACE, "Fehlermeldung")) { it.trace("Fehlermeldung") },
            Testfall(TestLogger.Event(TRACE, "Fehlermeldung Parameter 1")) {
                it.trace(
                    "Fehlermeldung {}",
                    "Parameter 1"
                )
            },
            Testfall(
                TestLogger.Event(
                    TRACE,
                    "Fehlermeldung Parameter 1 und Parameter 2"
                )
            ) { it.trace("Fehlermeldung {} und {}", "Parameter 1", "Parameter 2") },
            Testfall(
                TestLogger.Event(
                    TRACE,
                    "Fehlermeldung mit Parameter 1, Parameter 2 und Parameter 3"
                )
            ) { it.trace("Fehlermeldung mit {}, {} und {}", "Parameter 1", "Parameter 2", "Parameter 3") },
            Testfall(TestLogger.Event(TRACE, "Fehlermeldung mit Ausnahme")) {
                it.trace(
                    "Fehlermeldung mit Ausnahme",
                    ausnahme
                )
            }
        ).map { testfall ->
            dynamicTest(testfall.ereignis.toString()) {
                val logger = TestLogger
                logger.clear()
                testfall.protokollEintragGenerator(logger)
                assertThat(logger.events).containsExactly(testfall.ereignis)
            }
        }
    }

    @Nested
    @DisplayName("Wenn ich eine Protokoll-Eintrag für Info-Level schreibe")
    inner class WennIchEineProtokollEintragSchreibe {

        @BeforeEach
        fun `wenn ich eine Meldung protokolliere`() {
            logger.info("Fehlermeldung")
        }

        @Test
        fun `Dann merke ich mir ein Info Ereignis mit Nachricht`() {
            assertThat(logger.events)
                .contains(TestLogger.Event(INFO, "Fehlermeldung"))
        }
    }
}