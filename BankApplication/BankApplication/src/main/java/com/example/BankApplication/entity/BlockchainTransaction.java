package com.example.BankApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Data
public class BlockchainTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String blockChainTransactionId;
    private String receiverAccountNumber;
    private String receiverWalletAddress;
    private BigDecimal amount;
    private BigDecimal tokenAmount;
    private String transactionTitle;
    private String senderAccountNumber;
    private Boolean isBankAccountHolder;
    private String receiverEmail;
    @CreationTimestamp
    private LocalDateTime createdAt;



}
