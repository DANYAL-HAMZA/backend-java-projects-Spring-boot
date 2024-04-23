package com.example.payment.service.Command.api.Event;

import com.example.payment.service.Command.api.Entity.Payment;
import com.example.payment.service.Command.api.Repository.PaymentRepository;
import com.example.payment.service.Events.PaymentCancelledEvent;
import com.example.payment.service.Events.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentEventHandler {
    private final PaymentRepository paymentRepository;

    public PaymentEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        Payment payment = Payment.builder()
                .paymentId(paymentProcessedEvent.getPaymentId())
                .orderId(paymentProcessedEvent.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();
        paymentRepository.save(payment);
    }
    @EventHandler
    public void on(PaymentCancelledEvent paymentCancelledEvent){
        Payment payment = paymentRepository.findById(paymentCancelledEvent.getPaymentId()).get();
        payment.setPaymentStatus(paymentCancelledEvent.getPaymentStatus());
        paymentRepository.save(payment);
    }
}
