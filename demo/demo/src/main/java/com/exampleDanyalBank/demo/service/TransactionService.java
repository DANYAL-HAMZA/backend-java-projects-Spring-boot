package com.exampleDanyalBank.demo.service;

import com.exampleDanyalBank.demo.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transaction);
}
