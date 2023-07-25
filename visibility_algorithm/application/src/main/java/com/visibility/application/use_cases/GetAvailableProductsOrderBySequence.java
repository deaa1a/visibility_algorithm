package com.visibility.application.use_cases;

import com.visibility.application.product.ProductFilter;
import com.visibility.domain.entities.product.Product;
import com.visibility.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class GetAvailableProductsOrderBySequence {
    private ProductRepository productRepository;

    public Flux<Product> execute(List<ProductFilter> filters) {
        return Flux.from(productRepository.retrieveAllProducts())
                .filter(product -> filters.stream().anyMatch(filter -> filter.passesFilter(product)))
                .sort(Comparator.comparingInt(product -> product.getPosition().getValue()));
    }

}
