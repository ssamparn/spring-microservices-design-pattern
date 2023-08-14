package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence.mapper;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {

    public ProductEntity toProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    public Product toProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .build();
    }
}
