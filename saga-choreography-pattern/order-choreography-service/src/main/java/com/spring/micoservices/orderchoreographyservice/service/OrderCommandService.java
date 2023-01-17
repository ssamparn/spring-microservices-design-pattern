package com.spring.micoservices.orderchoreographyservice.service;

import com.spring.micoservices.orderchoreographyservice.entity.PurchaseOrder;
import com.spring.micoservices.orderchoreographyservice.repository.PurchaseOrderRepository;
import com.spring.microservices.choreography.dto.OrderRequestDto;
import com.spring.microservices.choreography.events.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderCommandService {

    @Autowired
    private Map<Integer, Integer> productPriceMap;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private OrderStatusPublisher publisher;

    public PurchaseOrder createOrder(OrderRequestDto orderRequest) {
        PurchaseOrder purchaseOrderEntity = this.dtoToEntity(orderRequest);
        PurchaseOrder persistedPurchaseOrder = this.purchaseOrderRepository.save(purchaseOrderEntity);

        this.publisher.raiseOrderEvent(persistedPurchaseOrder, OrderStatus.ORDER_CREATED);

        return persistedPurchaseOrder;
    }

    private PurchaseOrder dtoToEntity(OrderRequestDto orderRequest) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(orderRequest.getOrderId());
        purchaseOrder.setProductId(orderRequest.getProductId());
        purchaseOrder.setUserId(orderRequest.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(productPriceMap.get(orderRequest.getProductId()));

        return purchaseOrder;
    }
}
