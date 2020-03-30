package com.devxsquad.harmony.model.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

import static com.devxsquad.harmony.component.util.OrderStatus.ORDER_STATUS_TO_STRING;

@Data
public class OrderDto {

    private String id;

    @NotEmpty(message = "status field should not be empty")
    @Pattern(regexp = "(" + ORDER_STATUS_TO_STRING + ")",
        message = "Order status is invalid. options: (" + ORDER_STATUS_TO_STRING + ")")
    private String status;

    @NotNull(message = "orderItems should not be null")
    private List<OrderItemDto> orderItems;

    @NotNull(message = "orderBy should not be null")
    private OrderByDto orderBy;
}
