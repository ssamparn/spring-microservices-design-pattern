package com.spring.micoservices.paymentorchestrationservice.service;

import com.spring.microservices.orchestration.dto.PaymentRequestDto;
import com.spring.microservices.orchestration.dto.PaymentResponseDto;
import com.spring.microservices.orchestration.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private Map<Integer, Double> userBalanceMap;

    @PostConstruct
    private void init(){
        this.userBalanceMap = new HashMap<>();
        this.userBalanceMap.put(1, 1000d);
        this.userBalanceMap.put(2, 1000d);
        this.userBalanceMap.put(3, 1000d);
    }

    public PaymentResponseDto debit(final PaymentRequestDto requestDto){
        double balance = this.userBalanceMap.getOrDefault(requestDto.getUserId(), 0d);
        PaymentResponseDto responseDTO = new PaymentResponseDto();
        responseDTO.setAmount(requestDto.getAmount());
        responseDTO.setUserId(requestDto.getUserId());
        responseDTO.setOrderId(requestDto.getOrderId());
        responseDTO.setStatus(PaymentStatus.PAYMENT_REJECTED);
        if(balance >= requestDto.getAmount()){
            responseDTO.setStatus(PaymentStatus.PAYMENT_APPROVED);
            this.userBalanceMap.put(requestDto.getUserId(), balance - requestDto.getAmount());
        }
        return responseDTO;
    }

    public void credit(final PaymentRequestDto requestDto){
        this.userBalanceMap.computeIfPresent(requestDto.getUserId(), (k, v) -> v + requestDto.getAmount());
    }

}
