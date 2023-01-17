package com.spring.micoservices.orderchoreographyservice.controller;

import com.spring.micoservices.orderchoreographyservice.entity.PurchaseOrder;
import com.spring.micoservices.orderchoreographyservice.service.OrderCommandService;
import com.spring.micoservices.orderchoreographyservice.service.OrderQueryService;
import com.spring.microservices.choreography.dto.OrderRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderCommandService orderCommandService;

    @Autowired
    private OrderQueryService orderQueryService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequest) {
        orderRequest.setOrderId(UUID.randomUUID());
        return this.orderCommandService.createOrder(orderRequest);
    }

    @GetMapping("/all")
    public List<PurchaseOrder> getOrders(){
        return this.orderQueryService.getAll();
    }

}
