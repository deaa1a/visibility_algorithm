package com.visibility.domain.entities.product;

import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeQuantity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;


@Builder
@Getter
public class Product {
    private ProductId id;
    private ProductPriorityPosition position;

    private List<Size> sizes;

    public Boolean isBackSoon(){
        return sizes.stream()
                .anyMatch(size -> size.getIsBackSoon().getValue() && !size.getIsSpecialSize().getValue());
    }

    public Boolean hasSpecialSizeInStockOrBackSoon(){
        return sizes.stream()
                .anyMatch(size ->
                        size.getIsSpecialSize().getValue()
                                && (size.isSizeQuantityAvailable() || size.getIsBackSoon().getValue()));
    }

    public Boolean hasSizeInStockOrBackSoon(){
        return sizes.stream()
                .anyMatch(size ->
                        !size.getIsSpecialSize().getValue()
                                && (size.isSizeQuantityAvailable() || size.getIsBackSoon().getValue()));
    }



}
