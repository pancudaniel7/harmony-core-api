package com.devxsquad.platform.partsselling.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NotNull
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String productState;
    private String price;
}
