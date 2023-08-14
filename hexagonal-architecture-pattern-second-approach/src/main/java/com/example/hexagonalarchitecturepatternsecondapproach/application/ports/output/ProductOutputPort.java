package com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;

import java.util.Optional;

public interface ProductOutputPort {
    Product saveProduct(Product product);

    Optional<Product> getProductById(Long productId);
}
