package com.devxsquad.harmony.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderItemDto {

    @NotEmpty(message = "name field should not be empty")
    private String name;

    @NotEmpty(message = "price field should not be empty")
    private String price;

    @NotEmpty(message = "amount field should not be empty")
    @Min(value = 1, message = "amount field min value is 1")
    private String amount;
    private String details;
}
