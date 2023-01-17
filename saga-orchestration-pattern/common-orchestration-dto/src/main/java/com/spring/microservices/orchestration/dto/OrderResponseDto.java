package com.spring.microservices.orchestration.dto;

import com.spring.microservices.orchestration.enums.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderResponseDto {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private Double amount;
    private OrderStatus status;

}
