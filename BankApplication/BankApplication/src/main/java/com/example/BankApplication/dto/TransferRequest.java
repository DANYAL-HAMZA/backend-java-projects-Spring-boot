package com.example.BankApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder

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
