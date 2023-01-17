package com.spring.micoservices.orderorchestrationservice.service;

import com.spring.micoservices.orderorchestrationservice.repository.PurchaseOrderRepository;
import com.spring.microservices.orchestration.dto.OrchestratorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderEventUpdateService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Mono<Void> updateOrder(final OrchestratorResponseDto responseDto){
        return this.purchaseOrderRepository.findById(responseDto.getOrderId())
                .doOnNext(p -> p.setStatus(responseDto.getStatus()))
                .flatMap(this.purchaseOrderRepository::save)
                .then();
    }

}
