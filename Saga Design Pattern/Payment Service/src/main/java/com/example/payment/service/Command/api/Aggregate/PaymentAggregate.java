package com.example.payment.service.Command.api.Aggregate;

import com.example.payment.service.Command.CancelPaymentCommand;
import com.example.payment.service.Command.ValidatePaymentCommand;
import com.example.payment.service.Events.PaymentCancelledEvent;
import com.example.payment.service.Events.PaymentProcessedEvent;
import com.example.payment.service.Module.CardDetails;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Slf4j
@Aggregate
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate(){}
    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand){
        // all validations are made here (whether user is present in database or not)
        //publish the payment processed event
        log.info("executing validatePayment command for" + "orderId: {} and paymentId: {}",
                validatePaymentCommand.getOrderId(),
                validatePaymentCommand.getPaymentId());
        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(),
                validatePaymentCommand.getOrderId());
        AggregateLifecycle.apply(paymentProcessedEvent);
    }
    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
    @CommandHandler
    public void handle(CancelPaymentCommand cancelPaymentCommand){
        PaymentCancelledEvent paymentCancelledEvent = new PaymentCancelledEvent();
        BeanUtils.copyProperties(cancelPaymentCommand,paymentCancelledEvent);
        AggregateLifecycle.apply(paymentCancelledEvent);
    }
    @EventSourcingHandler
    public void on(PaymentCancelledEvent paymentCancelledEvent){
        //we then update the property that changes the state of the cancel payment command
        this.paymentStatus = paymentCancelledEvent.getPaymentStatus();
    }
}

