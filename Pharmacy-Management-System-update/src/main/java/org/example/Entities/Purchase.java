package org.example.Entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Purchase {
    int purchaseID;
    int drugID;
    String purchaseDate;
    int quantity;
    double totalAmount;
    String buyerName;
}
