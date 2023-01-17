package com.spring.micoservices.orderorchestrator.config;

import com.spring.micoservices.orderorchestrator.service.OrchestratorService;
import com.spring.microservices.orchestration.dto.OrchestratorRequestDto;
import com.spring.microservices.orchestration.dto.OrchestratorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrchestratorConfig {

    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDto>, Flux<OrchestratorResponseDto>> processor(){
        return flux -> flux
                            .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                            .doOnNext(dto -> System.out.println("Status : " + dto.getStatus()));
    }

}
