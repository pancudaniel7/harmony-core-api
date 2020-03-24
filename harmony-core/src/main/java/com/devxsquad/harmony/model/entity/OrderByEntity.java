package com.devxsquad.harmony.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.Generated;
import lombok.Data;

@Data
@Entity
@Table(name = "order_entity")
public class OrderByEntity {
    @Id
    @GeneratedValue
    private String id;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;

    @Column
    private String name;

    @Column
    private String discount;
}
