package com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.rest;

import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.input.CreateProductUseCase;
import com.example.hexagonalarchitecturepatternsecondapproach.application.ports.input.GetProductUseCase;
import com.example.hexagonalarchitecturepatternsecondapproach.domain.model.Product;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.request.ProductCreateRequest;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.response.ProductCreateResponse;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.data.response.ProductQueryResponse;
import com.example.hexagonalarchitecturepatternsecondapproach.infrastructure.adapters.input.mapper.ProductRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductRestAdapter {

    private final CreateProductUseCase createProductUseCase;

    private final GetProductUseCase getProductUseCase;

    private final ProductRestMapper productRestMapper;

    @PostMapping(value = "/products")
    public ResponseEntity<ProductCreateResponse> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        Product product = productRestMapper.toProduct(productCreateRequest);
        Product createdProduct = createProductUseCase.createProduct(product);
        ProductCreateResponse productCreateResponse = productRestMapper.toProductCreateResponse(createdProduct);

        return new ResponseEntity<>(productCreateResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<ProductQueryResponse> getProduct(@PathVariable Long productId) {
        Product productById = getProductUseCase.getProductById(productId);
        ProductQueryResponse productQueryResponse = productRestMapper.toProductQueryResponse(productById);

        return new ResponseEntity<>(productQueryResponse, HttpStatus.OK);
    }
}
