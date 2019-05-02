package com.github.haschi.tictactoe.backend.controller

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [AnwenderController::class])
@Import
@ActiveProfiles
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext
annotation class ControllerTest(
    @get:AliasFor(annotation = ActiveProfiles::class, attribute = "value")
    val profiles: Array<String>,
//    @get:AliasFor(annotation = WebFluxTest::class, attribute = "controllers")
//    val controllers: Array<KClass<*>>,
    @get:AliasFor(annotation = Import::class, attribute = "value")
    val imports: Array<KClass<*>>
) {
}