package com.spring.micoservices.orderorchestrator.service;

import com.spring.micoservices.orderorchestrator.service.steps.InventoryStep;
import com.spring.micoservices.orderorchestrator.service.steps.PaymentStep;
import com.spring.microservices.orchestration.dto.InventoryRequestDto;
import com.spring.microservices.orchestration.dto.OrchestratorRequestDto;
import com.spring.microservices.orchestration.dto.OrchestratorResponseDto;
import com.spring.microservices.orchestration.dto.PaymentRequestDto;
import com.spring.microservices.orchestration.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("payment")
    private WebClient paymentClient;

    @Autowired
    @Qualifier("inventory")
    private WebClient inventoryClient;

    public Mono<OrchestratorResponseDto> orderProduct(final OrchestratorRequestDto requestDto) {
        Workflow orderWorkflow = this.getOrderWorkflow(requestDto);
        return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkflowStep::process)
                .handle(((aBoolean, synchronousSink) -> {
                    if(aBoolean)
                        synchronousSink.next(true);
                    else
                        synchronousSink.error(new WorkflowException("create order failed!"));
                }))
                .then(Mono.fromCallable(() -> getResponseDTO(requestDto, OrderStatus.ORDER_COMPLETED)))
                .onErrorResume(ex -> this.revertOrder(orderWorkflow, requestDto));

    }

    private Mono<OrchestratorResponseDto> revertOrder(final Workflow workflow, final OrchestratorRequestDto requestDto) {
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(WorkflowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDto, OrderStatus.ORDER_CANCELLED)));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDto requestDto) {
        WorkflowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDTO(requestDto));
        WorkflowStep inventoryStep = new InventoryStep(this.inventoryClient, this.getInventoryRequestDTO(requestDto));
        return new OrderWorkflow(List.of(paymentStep, inventoryStep));
    }

    private OrchestratorResponseDto getResponseDTO(OrchestratorRequestDto requestDto, OrderStatus status) {
        OrchestratorResponseDto responseDto = new OrchestratorResponseDto();
        responseDto.setOrderId(requestDto.getOrderId());
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setProductId(requestDto.getProductId());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(status);
        return responseDto;
    }

    private PaymentRequestDto getPaymentRequestDTO(OrchestratorRequestDto requestDto) {
        PaymentRequestDto paymentRequestDTO = new PaymentRequestDto();
        paymentRequestDTO.setUserId(requestDto.getUserId());
        paymentRequestDTO.setAmount(requestDto.getAmount());
        paymentRequestDTO.setOrderId(requestDto.getOrderId());
        return paymentRequestDTO;
    }

    private InventoryRequestDto getInventoryRequestDTO(OrchestratorRequestDto requestDto) {
        InventoryRequestDto inventoryRequestDTO = new InventoryRequestDto();
        inventoryRequestDTO.setUserId(requestDto.getUserId());
        inventoryRequestDTO.setProductId(requestDto.getProductId());
        inventoryRequestDTO.setOrderId(requestDto.getOrderId());
        return inventoryRequestDTO;
    }

}
