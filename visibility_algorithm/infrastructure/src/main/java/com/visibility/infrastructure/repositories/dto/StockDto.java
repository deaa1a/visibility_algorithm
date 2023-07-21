package com.visibility.infrastructure.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private Long sizeId;
    private int quantity;
}
