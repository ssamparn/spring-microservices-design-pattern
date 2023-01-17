package com.spring.micoservices.inventoryorchestrationservice.controller;

import com.spring.micoservices.inventoryorchestrationservice.service.InventoryService;
import com.spring.microservices.orchestration.dto.InventoryRequestDto;
import com.spring.microservices.orchestration.dto.InventoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/deduct")
    public InventoryResponseDto deduct(@RequestBody final InventoryRequestDto requestDTO){
        return this.service.deductInventory(requestDTO);
    }

    @PostMapping("/add")
    public void add(@RequestBody final InventoryRequestDto requestDTO){
        this.service.addInventory(requestDTO);
    }

}
