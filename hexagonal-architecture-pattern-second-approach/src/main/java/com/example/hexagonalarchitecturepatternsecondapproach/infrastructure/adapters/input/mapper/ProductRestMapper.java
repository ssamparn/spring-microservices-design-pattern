package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.mapper;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.request.ProductCreateRequest;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.response.ProductCreateResponse;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.response.ProductQueryResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductRestMapper {

    public Product toProduct(ProductCreateRequest productCreateRequest) {
        return Product.builder()
                .id(productCreateRequest.getId())
                .name(productCreateRequest.getName())
                .description(productCreateRequest.getDescription())
                .build();
    }

    public ProductCreateResponse toProductCreateResponse(Product product) {
        return ProductCreateResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    public ProductQueryResponse toProductQueryResponse(Product product) {
        return ProductQueryResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }
}
