package com.example.payment.service.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCancelledEvent {
    private String orderId;
    private String orderStatus;
}
