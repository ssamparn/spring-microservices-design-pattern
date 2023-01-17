package com.spring.micoservices.orderorchestrationservice.eventhandlers;

import com.spring.micoservices.orderorchestrationservice.service.OrderEventUpdateService;
import com.spring.microservices.orchestration.dto.OrchestratorRequestDto;
import com.spring.microservices.orchestration.dto.OrchestratorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderEventHandler {

    @Autowired
    private Flux<OrchestratorRequestDto> flux;

    @Autowired
    private OrderEventUpdateService orderEventUpdateService;

    @Bean
    public Supplier<Flux<OrchestratorRequestDto>> supplier(){
        return () -> flux;
    };

    @Bean
    public Consumer<Flux<OrchestratorResponseDto>> consumer() {
        return f -> f
                .doOnNext(c -> System.out.println("Consuming :: " + c))
                .flatMap(responseDto -> this.orderEventUpdateService.updateOrder(responseDto))
                .subscribe();
    };

}
