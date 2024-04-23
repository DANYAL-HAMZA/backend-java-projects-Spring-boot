package com.example.payment.service.Commands.Api.Command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private String quantity;
    private String orderStatus;
}
