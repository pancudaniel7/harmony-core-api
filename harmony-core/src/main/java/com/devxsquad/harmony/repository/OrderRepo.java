package com.devxsquad.harmony.repository;

import com.devxsquad.harmony.model.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<OrderEntity, String> {

}