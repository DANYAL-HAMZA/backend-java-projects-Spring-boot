package com.exampleDanyalBank.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRequest {
    @Schema(
            name = "Account number of sender"
    )
    private String accountNumber;
    @Schema(
            name = "Amount to send"
    )
    private BigDecimal amount;
}
