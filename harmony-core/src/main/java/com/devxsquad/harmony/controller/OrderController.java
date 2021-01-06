package com.devxsquad.harmony.controller;


import com.devxsquad.harmony.component.validator.constraint.OrderConstraint;
import com.devxsquad.harmony.model.dto.OrderDto;
import com.devxsquad.harmony.service.OrderingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderingService orderingService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@OrderConstraint @RequestBody OrderDto orderDto) {
        orderDto.setId(null);
        OrderDto responseDto = orderingService.createOrder(orderDto);
        return new ResponseEntity<>(responseDto, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderBy(@PathVariable("id") String orderId) {
        OrderDto orderDto = orderingService.getOrderBy(orderId);
        return ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDto>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size) {
        Page<OrderDto> orderDtoPage = orderingService.getAllOrders(PageRequest.of(page, size));
        return ok(orderDtoPage);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderBy(@PathVariable("id") String orderId) {
        orderingService.deleteOrderBy(orderId);
    }
}
