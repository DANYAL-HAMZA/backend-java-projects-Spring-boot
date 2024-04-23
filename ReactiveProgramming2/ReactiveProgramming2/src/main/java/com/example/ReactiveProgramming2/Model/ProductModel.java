package com.example.ReactiveProgramming2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductModel {
    private String id;
    private String name;
    private int quantity;
    private double price;
}
