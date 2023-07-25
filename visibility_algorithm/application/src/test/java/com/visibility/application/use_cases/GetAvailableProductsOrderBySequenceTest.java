package com.visibility.application.use_cases;

import com.visibility.application.product.ProductFilter;
import com.visibility.application.product.filter.BackSoonFilter;
import com.visibility.application.product.filter.SpecialSizeInStockOrBackSoonFilter;
import com.visibility.domain.entities.product.Product;
import com.visibility.domain.entities.product.ProductId;
import com.visibility.domain.entities.product.ProductPriorityPosition;
import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeBackSoon;
import com.visibility.domain.entities.size.SizeId;
import com.visibility.domain.entities.size.SizeQuantity;
import com.visibility.domain.entities.size.SizeSpecial;
import com.visibility.domain.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class GetAvailableProductsOrderBySequenceTest {

    @Mock
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    private Product product3;
    private Product product4;

    private Product product5;

    private GetAvailableProductsOrderBySequence getAvailableProductsOrderBySequence;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getAvailableProductsOrderBySequence = new GetAvailableProductsOrderBySequence(productRepository);
        product1 = buildProduct1();
        product2 = buildProduct2();
        product3 = buildProduct3();
        product4 = buildProduct4();
        product5 = buildProduct5();

    }

    @Test
    public void testExecute_exampleChallenge() {


        when(productRepository.retrieveAllProducts()).thenReturn(Flux.just(product1, product2,product3,product4,product5));

        List<ProductFilter> filters = new ArrayList<>();
        filters.add(new BackSoonFilter());
        filters.add(new SpecialSizeInStockOrBackSoonFilter());
        Flux<Product> resultFlux = getAvailableProductsOrderBySequence.execute(filters);

        StepVerifier.create(resultFlux)
                .expectNext(product5)
                .expectNext(product1)
                .expectNext(product3)
                .expectComplete()
                .verify();
    }

    static Product buildProduct1(){

        var size1 = Size.builder()
                .id(SizeId.builder().value(11).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size2 = Size.builder()
                .id(SizeId.builder().value(12).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size3 = Size.builder()
                .id(SizeId.builder().value(13).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var sizes = Arrays.asList(size1,size2,size3);

        return Product.builder()
                .id(ProductId.builder().value(1).build())
                .position(ProductPriorityPosition.builder().value(10).build())
                .sizes(sizes)
                .build();
    }

    static Product buildProduct2(){

        var size1 = Size.builder()
                .id(SizeId.builder().value(21).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size2 = Size.builder()
                .id(SizeId.builder().value(22).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size3 = Size.builder()
                .id(SizeId.builder().value(23).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(true).build())
                .build();

        var sizes = Arrays.asList(size1,size2,size3);

        return Product.builder()
                .id(ProductId.builder().value(2).build())
                .position(ProductPriorityPosition.builder().value(7).build())
                .sizes(sizes)
                .build();
    }

    static Product buildProduct3(){

        var size1 = Size.builder()
                .id(SizeId.builder().value(31).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size2 = Size.builder()
                .id(SizeId.builder().value(32).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size3 = Size.builder()
                .id(SizeId.builder().value(13).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var sizes = Arrays.asList(size1,size2,size3);

        return Product.builder()
                .id(ProductId.builder().value(3).build())
                .position(ProductPriorityPosition.builder().value(15).build())
                .sizes(sizes)
                .build();
    }


    static Product buildProduct4(){

        var size1 = Size.builder()
                .id(SizeId.builder().value(41).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size2 = Size.builder()
                .id(SizeId.builder().value(42).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size3 = Size.builder()
                .id(SizeId.builder().value(43).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size4 = Size.builder()
                .id(SizeId.builder().value(44).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(true).build())
                .build();

        var sizes = Arrays.asList(size1,size2,size3, size4);

        return Product.builder()
                .id(ProductId.builder().value(4).build())
                .position(ProductPriorityPosition.builder().value(13).build())
                .sizes(sizes)
                .build();
    }

    static Product buildProduct5(){

        var size1 = Size.builder()
                .id(SizeId.builder().value(51).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size2 = Size.builder()
                .id(SizeId.builder().value(52).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size3 = Size.builder()
                .id(SizeId.builder().value(53).build())
                .isBackSoon(SizeBackSoon.builder().value(false).build())
                .isSpecialSize(SizeSpecial.builder().value(false).build())
                .build();

        var size4 = Size.builder()
                .id(SizeId.builder().value(54).build())
                .isBackSoon(SizeBackSoon.builder().value(true).build())
                .isSpecialSize(SizeSpecial.builder().value(true).build())
                .build();

        var sizes = Arrays.asList(size1,size2,size3,size4);

        return Product.builder()
                .id(ProductId.builder().value(5).build())
                .position(ProductPriorityPosition.builder().value(6).build())
                .sizes(sizes)
                .build();
    }
}
