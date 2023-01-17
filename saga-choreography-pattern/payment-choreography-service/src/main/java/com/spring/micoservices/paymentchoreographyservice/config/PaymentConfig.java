package com.spring.micoservices.paymentchoreographyservice.config;

import com.spring.micoservices.paymentchoreographyservice.service.PaymentService;
import com.spring.microservices.choreography.events.order.OrderEvent;
import com.spring.microservices.choreography.events.order.OrderStatus;
import com.spring.microservices.choreography.events.payment.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return eventFlux -> eventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        if (orderEvent.getOrderStatus() == OrderStatus.ORDER_CREATED) {
            return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
        } else {
            return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
