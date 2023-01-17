package com.spring.micoservices.orderchoreographyservice.config;

import com.spring.microservices.choreography.events.order.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class OrderConfig {

    @Bean
    public Sinks.Many<OrderEvent> orderEventSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    // Order Event Producer to Kafka Broker
    @Bean
    public Supplier<Flux<OrderEvent>> orderSupplier(Sinks.Many<OrderEvent> sink){
        return sink::asFlux;
    }
}
