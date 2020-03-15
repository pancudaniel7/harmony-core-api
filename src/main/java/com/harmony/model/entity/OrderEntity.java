package com.harmony.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OrderEntity {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String price;
}
