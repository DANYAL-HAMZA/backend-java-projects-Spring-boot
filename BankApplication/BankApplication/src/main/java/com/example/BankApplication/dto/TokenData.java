package com.example.BankApplication.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
public class TokenData {
    private String accountNumber;
    private String accountName;
    private BigDecimal tokenBalance;
    private BigDecimal accountBalance;

}
