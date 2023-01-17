package com.spring.micoservices.orderchoreographyservice.config;

import com.spring.micoservices.orderchoreographyservice.entity.PurchaseOrder;
import com.spring.micoservices.orderchoreographyservice.repository.PurchaseOrderRepository;
import com.spring.micoservices.orderchoreographyservice.service.OrderStatusPublisher;
import com.spring.microservices.choreography.events.inventory.InventoryStatus;
import com.spring.microservices.choreography.events.order.OrderStatus;
import com.spring.microservices.choreography.events.payment.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class OrderStatusUpdateEventHandler {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(final UUID orderId, Consumer<PurchaseOrder> consumer) {
        this.orderRepository
                .findById(orderId)
                .ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        if (Objects.isNull(purchaseOrder.getInventoryStatus()) || Objects.isNull(purchaseOrder.getPaymentStatus()))
            return;
        boolean isComplete = PaymentStatus.RESERVED == purchaseOrder.getPaymentStatus() && InventoryStatus.RESERVED == purchaseOrder.getInventoryStatus();
        OrderStatus orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isComplete){
            this.orderStatusPublisher.raiseOrderEvent(purchaseOrder, orderStatus);
        }
    }
}
