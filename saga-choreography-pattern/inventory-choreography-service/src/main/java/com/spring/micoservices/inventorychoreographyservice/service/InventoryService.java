package com.spring.micoservices.inventorychoreographyservice.service;

import com.spring.micoservices.inventorychoreographyservice.entity.OrderInventoryConsumption;
import com.spring.micoservices.inventorychoreographyservice.repository.OrderInventoryConsumptionRepository;
import com.spring.micoservices.inventorychoreographyservice.repository.OrderInventoryRepository;
import com.spring.microservices.choreography.dto.InventoryDto;
import com.spring.microservices.choreography.events.inventory.InventoryEvent;
import com.spring.microservices.choreography.events.inventory.InventoryStatus;
import com.spring.microservices.choreography.events.order.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private OrderInventoryRepository orderInventoryRepository;

    @Autowired
    private OrderInventoryConsumptionRepository orderInventoryConsumptionRepository;

    @Transactional
    public InventoryEvent newOrderInventory(OrderEvent orderEvent) {
        InventoryDto inventoryDto = InventoryDto.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProductId());
        return orderInventoryRepository.findById(orderEvent.getPurchaseOrder().getProductId())
                .filter(i -> i.getAvailableInventory() > 0 )
                .map(i -> {
                    i.setAvailableInventory(i.getAvailableInventory() - 1);
                    orderInventoryConsumptionRepository.save(OrderInventoryConsumption.of(orderEvent.getPurchaseOrder().getOrderId(), orderEvent.getPurchaseOrder().getProductId(), 1));
                    return new InventoryEvent(inventoryDto, InventoryStatus.RESERVED);
                })
                .orElse(new InventoryEvent(inventoryDto, InventoryStatus.REJECTED));
    }

    @Transactional
    public void cancelOrderInventory(OrderEvent orderEvent) {
        orderInventoryConsumptionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
            .ifPresent(ci -> {
                orderInventoryRepository.findById(ci.getProductId())
                        .ifPresent(i ->
                                i.setAvailableInventory(i.getAvailableInventory() + ci.getQuantityConsumed())
                        );
                orderInventoryConsumptionRepository.delete(ci);
            });
    }
}
