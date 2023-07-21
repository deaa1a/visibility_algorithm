package com.visibility.infrastructure.repositories;

import com.visibility.domain.entities.product.Product;
import com.visibility.infrastructure.repositories.ProductRepositoryImpl;
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
                .expectNextMatches(product1 -> product1.getId().getValue() == 1L && product1.getPosition().getValue() == 10 && product1.getSizes().size() == 3)
                .expectNextMatches(product2 -> product2.getId().getValue() == 2L && product2.getPosition().getValue() == 7 && product2.getSizes().size() == 3)
                .expectNextMatches(product3 -> product3.getId().getValue() == 3L && product3.getPosition().getValue() == 15 && product3.getSizes().size() == 3)
                .expectNextMatches(product4 -> product4.getId().getValue() == 4L && product4.getPosition().getValue() == 13 && product4.getSizes().size() == 4)
                .expectNextMatches(product5 -> product5.getId().getValue() == 5L && product5.getPosition().getValue() == 6 && product5.getSizes().size() == 4)
                .verifyComplete();
    }
}
