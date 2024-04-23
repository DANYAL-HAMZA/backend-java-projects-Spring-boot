package com.example.payment.service.Commands.Api.Aggregate;

import com.example.payment.service.Command.CancelOrderCommand;
import com.example.payment.service.Command.CompleteOrderCommand;
import com.example.payment.service.Commands.Api.Command.CreateOrderCommand;
import com.example.payment.service.Commands.Api.Event.OrderCreatedEvent;
import com.example.payment.service.Events.OrderCancelledEvent;
import com.example.payment.service.Events.OrderCompletedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CreateOrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private String quantity;
    private String orderStatus;
@CommandHandler
    public CreateOrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand,orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }
    public CreateOrderAggregate(){

    }
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
    this.userId= orderCreatedEvent.getUserId();
    this.orderId=orderCreatedEvent.getOrderId();
            this.addressId= orderCreatedEvent.getAddressId();
            this.productId=orderCreatedEvent.getProductId();
            this.quantity=orderCreatedEvent.getQuantity();
            this.orderStatus=orderCreatedEvent.getOrderStatus();
    }
    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand){
        OrderCompletedEvent orderCompletedEvent = new OrderCompletedEvent();
        BeanUtils.copyProperties(completeOrderCommand,orderCompletedEvent);
        AggregateLifecycle.apply(orderCompletedEvent);
    }
    @EventSourcingHandler
    public void on(OrderCompletedEvent orderCompletedEvent){
    this.orderStatus = orderCompletedEvent.getOrderStatus();
    }
    public void handle(CancelOrderCommand cancelOrderCommand){
        OrderCancelledEvent orderCancelledEvent = new OrderCancelledEvent();
        BeanUtils.copyProperties(cancelOrderCommand,orderCancelledEvent);
        AggregateLifecycle.apply(orderCancelledEvent);
    }
    public void on(OrderCancelledEvent orderCancelledEvent){
    this.orderStatus=orderCancelledEvent.getOrderStatus();
    }
}
