package backend.controller

import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.ExchangeStrategies

fun WebTestClient.withDecoder(decoder: Jackson2JsonDecoder): WebTestClient {
    return this.mutate()
        .exchangeStrategies(ExchangeStrategies.builder().codecs { configurer ->
            configurer.defaultCodecs().jackson2JsonDecoder(decoder)
        }.build()).build()
}

fun WebTestClient.withEncoder(encoder: Jackson2JsonEncoder): WebTestClient {
    return this.mutate()
        .exchangeStrategies(ExchangeStrategies.builder().codecs { configurer ->
            configurer.defaultCodecs().jackson2JsonEncoder(encoder)
        }
            .build())
        .build()
}