package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.customizedexception.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDateTime date;
    private String message;
    private List<String> details;
}
