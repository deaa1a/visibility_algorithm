package com.visibility.infrastructure.api.controller;


import com.visibility.application.product.ProductFilter;
import com.visibility.application.product.filter.BackSoonFilter;
import com.visibility.application.product.filter.SpecialSizeInStockOrBackSoonFilter;
import com.visibility.infrastructure.api.dto.response.ProductResponseDto;
import com.visibility.application.use_cases.GetAvailableProductsOrderBySequence;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final GetAvailableProductsOrderBySequence getAvailableProductsOrderBySequence;

    public ProductController(GetAvailableProductsOrderBySequence getAvailableProductsOrderBySequence) {
        this.getAvailableProductsOrderBySequence = getAvailableProductsOrderBySequence;
    }

    @GetMapping("/available")
    public ResponseEntity<Flux<ProductResponseDto>> getAvailableProducts() {
        List<ProductFilter> filters = new ArrayList<>();
        filters.add(new BackSoonFilter());
        filters.add(new SpecialSizeInStockOrBackSoonFilter());

        Flux<ProductResponseDto> products = getAvailableProductsOrderBySequence.execute(filters)
                .map(product -> ProductResponseDto.builder()
                        .productId(product.getId().getValue())
                        .sequence(product.getPosition().getValue())
                        .build());

        return ResponseEntity.ok(products);
    }
}