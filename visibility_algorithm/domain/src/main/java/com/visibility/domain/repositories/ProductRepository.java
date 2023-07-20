package com.visibility.domain.repositories;


import com.visibility.domain.entities.product.Product;
import org.reactivestreams.Publisher;

public interface ProductRepository {
    Publisher<Product> retrieveAllProducts();
}
