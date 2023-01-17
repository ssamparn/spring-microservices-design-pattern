package com.spring.micoservices.inventoryorchestrationservice.service;

import com.spring.microservices.orchestration.dto.InventoryRequestDto;
import com.spring.microservices.orchestration.dto.InventoryResponseDto;
import com.spring.microservices.orchestration.enums.InventoryStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {

    private Map<Integer, Integer> productInventoryMap;

    @PostConstruct
    private void init(){
        this.productInventoryMap = new HashMap<>();
        this.productInventoryMap.put(1, 5);
        this.productInventoryMap.put(2, 5);
        this.productInventoryMap.put(3, 5);
    }

    public InventoryResponseDto deductInventory(final InventoryRequestDto requestDto){
        int quantity = this.productInventoryMap.getOrDefault(requestDto.getProductId(), 0);
        InventoryResponseDto responseDto = new InventoryResponseDto();
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setProductId(requestDto.getProductId());
        responseDto.setStatus(InventoryStatus.UNAVAILABLE);
        if(quantity > 0){
            responseDto.setStatus(InventoryStatus.AVAILABLE);
            this.productInventoryMap.put(requestDto.getProductId(), quantity - 1);
        }
        return responseDto;
    }

    public void addInventory(final InventoryRequestDto requestDto){
        this.productInventoryMap
                .computeIfPresent(requestDto.getProductId(), (k, v) -> v + 1);
    }

}
