package com.spring.microservices.orchestration.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrchestratorRequestDto {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Double amount;


}
