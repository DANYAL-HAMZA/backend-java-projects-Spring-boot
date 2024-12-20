package com.example.BankApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BlockchainTransactionRequest {
        @Schema(
                name = "Receiver's account number"
        )
        private String receiverAccountNumber;
        @Schema(
                name = "Wallet Address of non account holder"
        )
        private String nonAccountHolderWalletAddress;
        @Schema(
                name = "Amount to convert"
        )
        private BigDecimal amount;
        @Schema(
                name = "Account number of sender"
        )
        private String senderAccountNumber;
        @Schema(
                name = "Email of non account holder"
        )
        private String nonAccountHolderEmail;



}
