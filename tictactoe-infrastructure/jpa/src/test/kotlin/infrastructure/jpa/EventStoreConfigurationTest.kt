package infrastructure.jpa

import org.assertj.core.api.Assertions.assertThat
import org.axonframework.eventhandling.GenericEventMessage
import org.axonframework.eventsourcing.eventstore.EventStore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class EventStoreConfigurationTest {
    @Autowired
    private lateinit var eventStore: EventStore

    data class EinEvent(val wert: String)

    @Test
    fun `Konfiguration stellt JPA Event Store bereit`() {
        assertThat(eventStore).isNotNull
        eventStore.publish(
            GenericEventMessage.asEventMessage<EinEvent>(EinEvent("Hello"))
        )
    }
}