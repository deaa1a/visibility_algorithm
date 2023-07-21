package com.visibility.domain.entities.size;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class Size {

    private SizeId id;
    private SizeBackSoon isBackSoon;
    @Builder.Default
    private Optional<SizeQuantity> quantity = Optional.empty();
    private SizeSpecial isSpecialSize;


     public boolean isSizeQuantityAvailable() {
        return getQuantity()
                .map(SizeQuantity::getValue)
                .filter(quantity -> quantity > 0)
                .isPresent();
    }

}
