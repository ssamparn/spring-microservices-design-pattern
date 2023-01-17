package com.spring.micoservices.orderchoreographyservice.entity;

import com.spring.microservices.choreography.events.inventory.InventoryStatus;
import com.spring.microservices.choreography.events.order.OrderStatus;
import com.spring.microservices.choreography.events.payment.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@ToString
public class PurchaseOrder {

    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private InventoryStatus inventoryStatus;

    @Version
    private int version;

}
