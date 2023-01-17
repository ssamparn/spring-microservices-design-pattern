package com.spring.microservices.orchestration.dto;

import com.spring.microservices.orchestration.enums.PaymentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentResponseDto {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}
