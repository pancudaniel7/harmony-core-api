package com.devxsquad.harmony.component.util.mapper;

import com.devxsquad.harmony.model.dto.OrderDto;
import com.devxsquad.harmony.model.dto.OrderItemDto;
import com.devxsquad.harmony.model.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderByMapper {

    OrderByMapper MAPPER = Mappers.getMapper(OrderByMapper.class);

    OrderItemEntity from(OrderDto OrderItemDto);

    OrderItemDto from(OrderItemEntity orderItemEntity);
}
