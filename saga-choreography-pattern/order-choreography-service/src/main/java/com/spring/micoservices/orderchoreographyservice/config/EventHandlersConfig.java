package com.spring.micoservices.orderchoreographyservice.config;

import com.spring.microservices.choreography.events.inventory.InventoryEvent;
import com.spring.microservices.choreography.events.payment.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventHandlersConfig {

    @Autowired
    private OrderStatusUpdateEventHandler orderEventHandler;

    // Payment Event Consumer from Kafka Broker
    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return paymentEvent -> {
            orderEventHandler.updateOrder(paymentEvent.getPayment().getOrderId(), purchaseOrder -> {
                purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
            });
        };
    }

    // Inventory Event Consumer from Kafka Broker
    @Bean
    public Consumer<InventoryEvent> inventoryEventConsumer() {
        return inventoryEvent -> {
            orderEventHandler.updateOrder(inventoryEvent.getInventory().getOrderId(), purchaseOrder -> {
                purchaseOrder.setInventoryStatus(inventoryEvent.getStatus());
            });
        };
    }
}
