package com.example.infrastructure.repositories;

import com.visibility.domain.entities.product.Product;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ProductRepositoryImplTest {

    ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl();
    }
    @Test
    public void retrieveAllProducts_shouldReturnFluxOfProducts() {
        // When
        Flux<Product> productFlux = productRepository.retrieveAllProducts();

        // Then
        StepVerifier.create(productFlux)
                .expectNextMatches(product1 -> product1.getId().getValue() == 1L) // Verify the first product emitted
                .expectNextMatches(product2 -> product2.getId().getValue() == 2L) // Verify the second product emitted
                .expectNextMatches(product3 -> product3.getId().getValue() == 3L) // Verify the third product emitted
                .expectNextMatches(product4 -> product4.getId().getValue() == 4L) // Verify the third product emitted
                .expectNextMatches(product5 -> product5.getId().getValue() == 5L) // Verify the third product emitte
                .verifyComplete(); //
    }
}
