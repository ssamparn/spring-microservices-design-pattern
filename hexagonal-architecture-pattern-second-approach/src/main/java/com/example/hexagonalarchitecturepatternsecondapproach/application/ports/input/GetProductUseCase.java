package com.example.hexagonalarchitecturepatternsecondapproach.application.ports.input;

import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;

public interface GetProductUseCase {
    Product getProductById(Long productId);
}
