package com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.event.ProductCreatedEvent;

public interface ProductEventPublisher {
    void publishProductCreatedEvent(ProductCreatedEvent event);
}
