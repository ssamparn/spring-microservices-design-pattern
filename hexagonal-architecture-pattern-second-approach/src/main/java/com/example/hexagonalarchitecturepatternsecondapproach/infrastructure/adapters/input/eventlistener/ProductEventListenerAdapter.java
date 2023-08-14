package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.eventlistener;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventListenerAdapter {

    @EventListener
    public void handle(ProductCreatedEvent event){
        log.info("Product created with id " + event.getId() + " at " + event.getDate());
    }

}
