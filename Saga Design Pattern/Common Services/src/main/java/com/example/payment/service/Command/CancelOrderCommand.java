package com.example.payment.service.Command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Value
//the @value annotation adds getters and allArgs constructor for our class
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String OrderId;
    private String orderStatus = "CANCELLED";
}
