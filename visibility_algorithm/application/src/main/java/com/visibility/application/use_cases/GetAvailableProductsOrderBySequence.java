package com.visibility.application.use_cases;

import com.visibility.domain.entities.product.Product;
import com.visibility.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class GetAvailableProductsOrderBySequence {
    private ProductRepository productRepository;

    public Flux<Product> execute() {
        return Flux.from(productRepository.retrieveAllProducts())
                .filter(product ->
                        product.isBackSoon()
                                || (product.hasSpecialSizeInStockOrBackSoon()
                                && product.hasSizeInStockOrBackSoon()))
                .sort(Comparator.comparingInt(product -> product.getPosition().getValue()));
    }

}
