package com.visibility.application.product.filter;

import com.visibility.application.product.ProductFilter;
import com.visibility.domain.entities.product.Product;

public class BackSoonFilter implements ProductFilter {
    @Override
    public boolean passesFilter(Product product) {
        return product.isBackSoon();
    }
}
