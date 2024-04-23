package com.example.payment.service.Command.api.Agregate;

import com.example.payment.service.Command.ShipOrderCommand;
import com.example.payment.service.Events.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String OrderId;
    private String shipmentStatus;

    public ShipmentAggregate(){}
    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand){
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .OrderId(shipOrderCommand.getOrderId())
                .shipmentStatus("COMPLETED")
                .shipmentId(shipOrderCommand.getShipmentId())
                .build();
        AggregateLifecycle.apply(orderShippedEvent);
    }
    @EventSourcingHandler
    public void on(OrderShippedEvent orderShippedEvent){
        this.OrderId = orderShippedEvent.getOrderId();
        this.shipmentStatus = orderShippedEvent.getShipmentStatus();
        this.shipmentId = orderShippedEvent.getShipmentId();
    }




}
