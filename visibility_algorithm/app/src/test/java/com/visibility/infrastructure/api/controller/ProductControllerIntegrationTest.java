package com.visibility.infrastructure.api.controller;

import com.visibility.domain.entities.product.Product;
import com.visibility.application.product.ProductFilter;
import com.visibility.application.product.filter.BackSoonFilter;
import com.visibility.application.product.filter.SpecialSizeInStockOrBackSoonFilter;
import com.visibility.domain.entities.product.ProductId;
import com.visibility.domain.entities.product.ProductPriorityPosition;
import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeBackSoon;
import com.visibility.domain.entities.size.SizeId;
import com.visibility.domain.entities.size.SizeSpecial;
import com.visibility.domain.repositories.ProductRepository;
import com.visibility.infrastructure.api.dto.response.ProductResponseDto;
import com.visibility.application.use_cases.GetAvailableProductsOrderBySequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@WebFluxTest(ProductController.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GetAvailableProductsOrderBySequence getAvailableProductsOrderBySequence;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testGetAvailableProducts() {
        webTestClient.get().uri("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDto.class)
                .hasSize(3)
                .consumeWith(response -> {
                    List<ProductResponseDto> productResponseList = response.getResponseBody();
                    assertNotNull(productResponseList);
                    assertEquals(3, productResponseList.size());
                    ProductResponseDto product1 = productResponseList.get(0);
                    assertEquals(5, product1.getProductId());
                    assertEquals(6, product1.getSequence());
                    ProductResponseDto product2 = productResponseList.get(1);
                    assertEquals(1, product2.getProductId());
                    assertEquals(10, product2.getSequence());
                    ProductResponseDto product3 = productResponseList.get(2);
                    assertEquals(3, product3.getProductId());
                    assertEquals(15, product3.getSequence());
                });
    }

}

