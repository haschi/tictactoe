package com.github.haschi.tictactoe.requirements.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class KonfigurationIT(@Value("\${spring.main.banner-mode}") val bannerMode: String) {
    @Test
    fun `Während des Tests wird kein Banner ausgegeben`() {
        assertThat(bannerMode).isEqualTo("off")
    }

    @Test
    fun `Es ist genau ein Profil für den Storage ausgewähl`(environment: Environment) {
        assertThat(environment.activeProfiles)
            .hasOnlyOneElementSatisfying { it == "h2" || it == "axon" }
    }
}