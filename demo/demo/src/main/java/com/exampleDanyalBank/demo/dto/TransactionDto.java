package com.exampleDanyalBank.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionDto {
    private String transactionType;
    private String accountNumber;
    private BigDecimal amount;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
