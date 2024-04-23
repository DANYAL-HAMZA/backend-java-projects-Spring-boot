package com.example.payment.service.Commands.Api.Saga;

import com.example.payment.service.Command.*;
import com.example.payment.service.Commands.Api.Event.OrderCreatedEvent;
import com.example.payment.service.Events.*;
import com.example.payment.service.Module.User;
import com.example.payment.service.Querry.GetUserPaymentDetailsQuerry;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private  transient QueryGateway queryGateway;

    public OrderProcessingSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }
    //you should generate both no args and all args constructor for the saga class.
    public OrderProcessingSaga(){

    }
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreatedEvent orderCreatedEvent){
        log.info("order created event for Saga for order id : {}",orderCreatedEvent.getOrderId()
        );

        GetUserPaymentDetailsQuerry querry = new GetUserPaymentDetailsQuerry(orderCreatedEvent.getUserId())
                ;
        User user = null;
        try {
            user = queryGateway.query(querry, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e){
            log.error(e.getMessage());
            //if an error occured during, we implement the compensating transaction
            //to cancel the order
            cancelOrderCommand(orderCreatedEvent.getOrderId());
        }
        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                //the card details here is useful for validating the type of card accepted and
                //handle a scenario where the card is not specified
                .cardDetails(user.getCardDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();
        commandGateway.sendAndWait(validatePaymentCommand);
    }
//here we will need tol create a cancel order command in the common services and use it here
    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = new CancelOrderCommand(orderId);
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent){
        log.info("order created event for Saga for order id : {}",paymentProcessedEvent.getOrderId()
        );
        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .orderId(paymentProcessedEvent.getOrderId())
                    .shipmentId(UUID.randomUUID().toString())
                    .build();
            commandGateway.sendAndWait(shipOrderCommand);
        } catch (Exception e){
            log.error(e.getMessage());
            //we do try catch to catch any error or exception during order shipment
            //if there is an error we implement the compensating transaction
            cancelPaymentCommand(paymentProcessedEvent);
        }
    }

    private void cancelPaymentCommand(PaymentProcessedEvent paymentProcessedEvent) {
        CancelPaymentCommand cancelPaymentCommand = new CancelPaymentCommand(paymentProcessedEvent.getPaymentId(),
                paymentProcessedEvent.getOrderId(),"CANCELLED" );
        commandGateway.send(cancelPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent orderShippedEvent){
        log.info("order shipped event for Saga for order id : {}",orderShippedEvent.getOrderId()
        );
        CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                .orderId(orderShippedEvent.getOrderId())
                .orderStatus("APPROVED")
                .build();
        commandGateway.sendAndWait(completeOrderCommand);
        //this completeOrderCommand will be handled by the aggregate in the orderService.
    }
    @SagaEventHandler(associationProperty = "orderId")
    //the EndSaga annotation ends the saga and indicates that there are no more commands to be handled
    @EndSaga
    public void handle(OrderCompletedEvent orderCompletedEvent){
        log.info("order completed event for Saga for order id : {}",orderCompletedEvent.getOrderId());
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelledEvent orderCancelledEvent){
        log.info("order cancelled event for Saga for order id : {}",orderCancelledEvent.getOrderId());
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent paymentCancelledEvent){
        log.info("payment cancelled event for Saga for order id : {}",paymentCancelledEvent.getOrderId());
//when the paymenso this method must call the cancel order command is cancelled,
// the order must be cancelled as well,
        cancelOrderCommand(paymentCancelledEvent.getOrderId());
    }
    }
















