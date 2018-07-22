package com.github.haschi.tictactoe

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class KonfigurationIT
{
    @Value("\${spring.main.banner-mode}")
    lateinit var bannerMode: String;

    @Test
    fun `Während des Tests wird kein Banner ausgegeben`()
    {
        assertThat(bannerMode).isEqualTo("off")
    }
}