package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.eventpublisher;

import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output.ProductEventPublisher;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class ProductEventPublisherAdapter implements ProductEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishProductCreatedEvent(ProductCreatedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
