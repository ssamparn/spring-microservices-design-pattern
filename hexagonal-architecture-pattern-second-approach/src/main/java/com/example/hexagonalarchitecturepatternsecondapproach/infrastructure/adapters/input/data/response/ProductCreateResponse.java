package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponse {

    private Long id;

    private String name;

    private String description;

}
