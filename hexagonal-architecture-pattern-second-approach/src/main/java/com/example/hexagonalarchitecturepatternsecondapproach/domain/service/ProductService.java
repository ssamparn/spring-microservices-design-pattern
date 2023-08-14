package com.example.hexagonalarchitecturepatternsecondapproach.domain.service;

import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.input.CreateProductUseCase;
import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.input.GetProductUseCase;
import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output.ProductEventPublisher;
import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output.ProductOutputPort;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.event.ProductCreatedEvent;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.exception.ProductNotFound;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService implements CreateProductUseCase, GetProductUseCase {

    private final ProductOutputPort productOutputPort;
    private final ProductEventPublisher productEventPublisher;

    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productOutputPort.saveProduct(product);
        productEventPublisher.publishProductCreatedEvent(new ProductCreatedEvent(product.getId()));
        return savedProduct;
    }

    @Override
    public Product getProductById(Long productId) {
        return productOutputPort.getProductById(productId)
                .orElseThrow(() -> new ProductNotFound("Product not found with id " + productId));
    }
}
