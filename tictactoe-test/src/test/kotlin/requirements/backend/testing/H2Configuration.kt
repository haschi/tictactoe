package requirements.backend.testing

import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine
import org.axonframework.modelling.saga.repository.jpa.SagaEntry
import org.axonframework.spring.config.AxonConfiguration
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("h2")
@EntityScan(basePackageClasses = [DomainEventEntry::class, SagaEntry::class])
class H2Configuration {
    @Bean
    fun eventStore(
        configuration: AxonConfiguration,
        entityManager: EntityManager,
        transactionManager: PlatformTransactionManager
    ): EventStore {

        val storage = JpaEventStorageEngine.builder()
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .transactionManager(SpringTransactionManager(transactionManager))
            .eventSerializer(configuration.eventSerializer())
            .build()

        return EmbeddedEventStore.builder().storageEngine(storage).build()
    }

}