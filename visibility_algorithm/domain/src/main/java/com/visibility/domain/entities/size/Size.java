package com.visibility.domain.entities.size;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Size {

    private SizeId id;
    private SizeBackSoon isBackSoon;
    private SizeQuantity quantity;
    private SizeSpecial isSpecialSize;



}
