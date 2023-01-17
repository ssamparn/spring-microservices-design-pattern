package com.spring.micoservices.orderorchestrationservice.service;

import com.spring.micoservices.orderorchestrationservice.entity.PurchaseOrder;
import com.spring.micoservices.orderorchestrationservice.repository.PurchaseOrderRepository;
import com.spring.microservices.orchestration.dto.OrchestratorRequestDto;
import com.spring.microservices.orchestration.dto.OrderRequestDto;
import com.spring.microservices.orchestration.dto.OrderResponseDto;
import com.spring.microservices.orchestration.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Service
public class OrderService {

    private static final Map<Integer, Double> PRODUCT_PRICE =  Map.of(
            1, 100d,
            2, 200d,
            3, 300d
    );

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private Sinks.Many<OrchestratorRequestDto> sink;

    public Mono<PurchaseOrder> createOrder(OrderRequestDto orderRequestDto){
        return this.purchaseOrderRepository.save(this.dtoToEntity(orderRequestDto))
                .doOnNext(e -> orderRequestDto.setOrderId(e.getId()))
                .doOnNext(e -> this.emitEvent(orderRequestDto));
    }

    public Flux<OrderResponseDto> getAll() {
        return this.purchaseOrderRepository.findAll()
                .map(this::entityToDto);
    }

    private void emitEvent(OrderRequestDto orderRequestDto){
        this.sink.tryEmitNext(this.getOrchestratorRequestDto(orderRequestDto));
    }

    private PurchaseOrder dtoToEntity(final OrderRequestDto dto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(dto.getOrderId());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(PRODUCT_PRICE.get(purchaseOrder.getProductId()));
        return purchaseOrder;
    }

    private OrderResponseDto entityToDto(final PurchaseOrder purchaseOrder){
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(purchaseOrder.getId());
        dto.setProductId(purchaseOrder.getProductId());
        dto.setUserId(purchaseOrder.getUserId());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setAmount(purchaseOrder.getPrice());
        return dto;
    }

    public OrchestratorRequestDto getOrchestratorRequestDto(OrderRequestDto orderRequestDto){
        OrchestratorRequestDto requestDto = new OrchestratorRequestDto();
        requestDto.setUserId(orderRequestDto.getUserId());
        requestDto.setAmount(PRODUCT_PRICE.get(orderRequestDto.getProductId()));
        requestDto.setOrderId(orderRequestDto.getOrderId());
        requestDto.setProductId(orderRequestDto.getProductId());
        return requestDto;
    }
}
