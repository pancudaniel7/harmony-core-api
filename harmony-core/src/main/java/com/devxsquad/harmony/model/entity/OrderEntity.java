package com.devxsquad.harmony.model.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    private String id;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItemEntities;

    @OneToOne(cascade = ALL)
    private OrderByEntity orderByEntity;
}
