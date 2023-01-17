package com.spring.microservices.orchestration.dto;

import com.spring.microservices.orchestration.enums.InventoryStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class InventoryResponseDto {

    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;

}
