package com.spring.micoservices.orderorchestrationservice.config;

import com.spring.microservices.orchestration.dto.OrchestratorRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class OrderConfig {

    @Bean
    public Sinks.Many<OrchestratorRequestDto> sink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequestDto> flux(Sinks.Many<OrchestratorRequestDto> sink){
        return sink.asFlux();
    }

}
