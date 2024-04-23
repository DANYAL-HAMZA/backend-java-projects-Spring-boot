package com.example.payment.service.Command;

import com.example.payment.service.Module.CardDetails;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ValidatePaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private CardDetails cardDetails;
//in this context we assume that the payment has already been done by the user,
// we are only validating (verifying) the payment before approving the order
// The payment logic can be implemented using a separate api.
}
