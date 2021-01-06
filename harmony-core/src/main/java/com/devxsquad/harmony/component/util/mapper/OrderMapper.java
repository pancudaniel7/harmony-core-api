package com.devxsquad.harmony.component.util.mapper;

import com.devxsquad.harmony.model.dto.OrderDto;
import com.devxsquad.harmony.model.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderEntity from(OrderDto orderDto);

    OrderDto from(OrderEntity orderEntity);
}
