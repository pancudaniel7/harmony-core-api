package com.devxsquad.harmony.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import com.devxsquad.harmony.model.dto.OrderDto;
import com.devxsquad.harmony.model.entity.OrderEntity;
import com.devxsquad.harmony.repository.OrderRepo;
import com.devxsquad.harmony.component.util.mapper.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;
import static org.mapstruct.factory.Mappers.getMapper;

@Service
@RequiredArgsConstructor
public class OrderingService {

    private final OrderRepo orderRepo;
    private final OrderMapper orderMapper = getMapper(OrderMapper.class);

    public OrderDto createOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderMapper.from(orderDto);

        orderEntity.setDate(LocalDate.now());
        orderEntity.setTime(LocalTime.now());
        orderEntity.setTimestamp(LocalDateTime.now());

        orderEntity = orderRepo.save(orderEntity);
        return orderMapper.from(orderEntity);
    }

    public OrderDto getOrderBy(String id) {
        Optional<OrderEntity> orderEntity = orderRepo.findById(id);
        if (orderEntity.isEmpty()) {
            throw new EntityNotFoundException(format("Entity not found for id: %s", id));
        }
        return orderMapper.from(orderEntity.get());
    }

    public PageImpl<OrderDto> getAllOrders(Pageable pageable) {
        Page<OrderEntity> entityPage = orderRepo.findAll(pageable);
        List<OrderDto> entityList = entityPage.stream().map(orderMapper::from).collect(Collectors.toList());
        return new PageImpl<>(entityList, pageable, entityPage.getTotalElements());
    }

    public void deleteOrderBy(String id) {
        orderRepo.deleteById(id);
    }
}
