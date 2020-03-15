package com.devxsquad.platform.partsselling.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@NotNull
@EqualsAndHashCode(callSuper = true)
public class PieseAutoDto extends ProductDto {
    private String category;
    private String subCategory;
}
