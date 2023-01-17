package com.spring.micoservices.orderorchestrationservice.controller;

import com.spring.micoservices.orderorchestrationservice.entity.PurchaseOrder;
import com.spring.micoservices.orderorchestrationservice.service.OrderService;
import com.spring.microservices.orchestration.dto.OrderRequestDto;
import com.spring.microservices.orchestration.dto.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/create")
    public Mono<PurchaseOrder> createOrder(@RequestBody Mono<OrderRequestDto> mono){
        return mono
                .flatMap(this.service::createOrder);
    }

    @GetMapping("/all")
    public Flux<OrderResponseDto> getOrders(){
        return this.service.getAll();
    }

}
