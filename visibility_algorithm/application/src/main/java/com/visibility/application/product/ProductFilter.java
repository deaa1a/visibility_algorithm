package com.visibility.application.product;

import com.visibility.domain.entities.product.Product;

public interface ProductFilter {
    boolean passesFilter(Product product);
}
