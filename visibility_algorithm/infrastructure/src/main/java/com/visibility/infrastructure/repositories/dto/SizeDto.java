package com.visibility.infrastructure.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SizeDto {

    private Long id;
    private Long productId;
    private boolean backSoon;
    private boolean special;
}
