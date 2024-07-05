package org.example.Entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Drug {
    int drugID;
    String name;
    int supplierID;
    int stock;
    double price;
}
