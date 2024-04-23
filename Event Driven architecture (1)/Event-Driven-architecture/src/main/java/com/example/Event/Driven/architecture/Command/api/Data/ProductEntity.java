package com.example.Event.Driven.architecture.Command.api.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ProductEntity {
    @Id
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;

}
