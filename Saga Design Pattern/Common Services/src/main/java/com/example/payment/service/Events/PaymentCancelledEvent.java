package com.example.payment.service.Events;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private String paymentStatus;
}
