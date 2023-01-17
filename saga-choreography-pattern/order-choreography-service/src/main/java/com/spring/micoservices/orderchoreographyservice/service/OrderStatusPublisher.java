package com.spring.micoservices.orderchoreographyservice.service;

import com.spring.micoservices.orderchoreographyservice.entity.PurchaseOrder;
import com.spring.microservices.choreography.dto.PurchaseOrderDto;
import com.spring.microservices.choreography.events.order.OrderEvent;
import com.spring.microservices.choreography.events.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderEventSink;

    public void raiseOrderEvent(final PurchaseOrder purchaseOrder, OrderStatus orderStatus) {
        PurchaseOrderDto purchaseOrderDto = PurchaseOrderDto.of(
                purchaseOrder.getId(),
                purchaseOrder.getProductId(),
                purchaseOrder.getPrice(),
                purchaseOrder.getUserId()
        );
        OrderEvent orderEvent = new OrderEvent(purchaseOrderDto, orderStatus);
        orderEventSink.tryEmitNext(orderEvent);
    }
}
