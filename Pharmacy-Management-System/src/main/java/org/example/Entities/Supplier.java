package org.example.Entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Supplier {
    int supplierID;
    String name;
    String location;
}
