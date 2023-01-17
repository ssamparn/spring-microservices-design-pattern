package com.spring.micoservices.paymentorchestrationservice.controller;

import com.spring.micoservices.paymentorchestrationservice.service.PaymentService;
import com.spring.microservices.orchestration.dto.PaymentRequestDto;
import com.spring.microservices.orchestration.dto.PaymentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/debit")
    public PaymentResponseDto debit(@RequestBody PaymentRequestDto requestDto){
        return this.service.debit(requestDto);
    }

    @PostMapping("/credit")
    public void credit(@RequestBody PaymentRequestDto requestDto){
        this.service.credit(requestDto);
    }

}
