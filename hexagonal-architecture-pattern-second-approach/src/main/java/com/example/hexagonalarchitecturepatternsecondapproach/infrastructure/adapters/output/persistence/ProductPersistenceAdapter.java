package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence;

import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.output.ProductOutputPort;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence.entity.ProductEntity;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence.mapper.ProductPersistenceMapper;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.output.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper productPersistenceMapper;

    @Override
    public Product saveProduct(Product product) {
        ProductEntity productEntity = productPersistenceMapper.toProductEntity(product);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productPersistenceMapper.toProduct(savedEntity);
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        Optional<ProductEntity> productEntityByIdOptional = productRepository.findById(productId);
        return productEntityByIdOptional.map(productPersistenceMapper::toProduct);
    }
}
