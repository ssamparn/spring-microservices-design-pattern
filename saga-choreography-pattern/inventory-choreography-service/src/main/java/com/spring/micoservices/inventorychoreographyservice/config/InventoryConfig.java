package com.spring.micoservices.inventorychoreographyservice.config;

import com.spring.micoservices.inventorychoreographyservice.service.InventoryService;
import com.spring.microservices.choreography.events.inventory.InventoryEvent;
import com.spring.microservices.choreography.events.order.OrderEvent;
import com.spring.microservices.choreography.events.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class InventoryConfig {

    @Autowired
    private InventoryService inventoryService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<InventoryEvent>> inventoryProcessor() {
        return eventFlux -> eventFlux.flatMap(this::processInventory);
    }

    private Mono<InventoryEvent> processInventory(OrderEvent orderEvent) {
        if (orderEvent.getOrderStatus() == OrderStatus.ORDER_CREATED) {
            return Mono.fromSupplier(() -> this.inventoryService.newOrderInventory(orderEvent));
        }
        return Mono.fromRunnable(() -> this.inventoryService.cancelOrderInventory(orderEvent));
    }
}
