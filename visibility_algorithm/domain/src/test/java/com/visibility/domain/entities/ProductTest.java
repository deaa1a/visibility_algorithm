package com.visibility.domain.entities;

import com.visibility.domain.entities.product.Product;
import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeBackSoon;
import com.visibility.domain.entities.size.SizeQuantity;
import com.visibility.domain.entities.size.SizeSpecial;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void testIsBackSoon_ReturnsTrueWhenAnySizeIsBackSoon() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.isBackSoon();

        // THEN
        assertTrue(result);
    }

    @Test
    public void testIsBackSoon_ReturnsFalseWhenAnySizeIsBackSoon() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(5).build()))
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(5).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.isBackSoon();

        // THEN
        assertFalse(result);
    }

    @Test
    public void testHasSpecialSizeInStockOrBackSoon_ReturnsTrueWhenAnySizeIsSpecialInStock() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(1).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSpecialSizeInStockOrBackSoon();

        // THEN
        assertTrue(result);
    }


    @Test
    public void testHasSpecialSizeInStockOrBackSoon_ReturnsTrueWhenAnySizeIsSpecialIsBackSoon() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.TRUE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSpecialSizeInStockOrBackSoon();

        // THEN
        assertTrue(result);
    }

    @Test
    public void testHasSpecialSizeInStockOrBackSoon_ReturnsFalseWhenAnySizeIsNotSpecialIsBackSoon() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.TRUE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSpecialSizeInStockOrBackSoon();

        // THEN
        assertFalse(result);
    }

    @Test
    public void testHasSpecialSizeInStockOrBackSoon_ReturnsFalseWhenAnySizeIsSpecialIsNotBackSoonAndThereIsNotStock() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSpecialSizeInStockOrBackSoon();

        // THEN
        assertFalse(result);
    }



    @Test
    public void hasSizeInStockOrBackSoon_ReturnsTrueWhenAnyIsBackSoon() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.TRUE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSizeInStockOrBackSoon();

        // THEN
        assertTrue(result);
    }

    @Test
    public void hasSizeInStockOrBackSoon_ReturnsTrueWhenAnyIsNotBackSoonAndThereIsStock() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(2).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSizeInStockOrBackSoon();

        // THEN
        assertTrue(result);
    }

    @Test
    public void hasSizeInStockOrBackSoon_ReturnsTrueWhenAnyIsNotBackSoonAndThereIsNotStock() {
        // GIVE
        Size size1 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.FALSE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        Size size2 = Size.builder()
                .isBackSoon(SizeBackSoon.builder().value(Boolean.FALSE).build())
                .isSpecialSize(SizeSpecial.builder().value(Boolean.TRUE).build())
                .quantity(Optional.of(SizeQuantity.builder().value(0).build()))
                .build();

        List<Size> sizes = Arrays.asList(size1, size2);
        Product product = Product.builder().sizes(sizes).build();

        // WHEN
        boolean result = product.hasSizeInStockOrBackSoon();

        // THEN
        assertFalse(result);
    }

}
