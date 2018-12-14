package com.github.haschi.tictactoe.requirements.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.EntityManagerFactory

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class KonfigurationIT(@Value("\${spring.main.banner-mode}") val bannerMode: String) {
    @Test
    fun `Während des Tests wird kein Banner ausgegeben`() {
        assertThat(bannerMode).isEqualTo("off")
    }

    @Autowired
    private lateinit var environment: Environment

    @Test
    @Disabled("Konflikt mit CoreIT")
    fun `Es ist genau ein Storage-Profil ausgewählt`() {
        assertThat(environment.activeProfiles)
            .hasOnlyOneElementSatisfying { it == "h2" || it == "axon" }
    }

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun `Entity Manager Factory ist im Profile h2 verfügbar`() {
        val factory = context.autowireCapableBeanFactory.getBean(EntityManagerFactory::class.java)
        assertThat(factory).isNotNull
    }
}