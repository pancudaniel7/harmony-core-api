package com.devxsquad.harmony.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue
    private String id;

    @NotNull
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String amount;

    @NotNull
    @Column
    private String price;
}
