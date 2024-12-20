package com.exampleDanyalBank.demo.service;

import com.exampleDanyalBank.demo.dto.TransactionDto;
import com.exampleDanyalBank.demo.entity.Transaction;
import com.exampleDanyalBank.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(TransactionDto transaction) {

        Transaction newTransaction = Transaction.builder()
                .transactionType(transaction.getTransactionType())
                .accountNumber(transaction.getAccountNumber())
                .amount(transaction.getAmount())
                .build();

        transactionRepository.save(newTransaction);
    }
}
