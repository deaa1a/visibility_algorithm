package com.visibility.infrastructure.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize
public class ProductResponseDto {

    @JsonProperty("productId")
    private int productId;

    @JsonProperty("sequence")
    private int sequence;
}
