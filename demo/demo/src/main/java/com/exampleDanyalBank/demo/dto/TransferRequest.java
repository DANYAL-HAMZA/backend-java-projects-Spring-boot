package com.exampleDanyalBank.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class TransferRequest {
    @Schema(
            name = "Account number of sender"
    )
    private String sourceAccountNumber;
    @Schema(
            name = "Account number of receiver"
    )
    private String destinationAccountNumber;
    @Schema(
            name = "Amount to send"
    )
    private BigDecimal amount;
}
