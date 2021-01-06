package com.devxsquad.harmony.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems;

    @OneToOne(cascade = ALL, orphanRemoval = true)
    private OrderByEntity orderBy;
}
