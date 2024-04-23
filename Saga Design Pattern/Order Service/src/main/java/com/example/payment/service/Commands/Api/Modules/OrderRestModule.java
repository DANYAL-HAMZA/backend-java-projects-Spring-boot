package com.example.payment.service.Commands.Api.Modules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestModule {
    private String productId;
    private String userId;
    private String addressId;
    private String quantity;
}
